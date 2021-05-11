package courseproject.javacheck.utils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import courseproject.javacheck.model.elasticsearchModels.Work;
import courseproject.javacheck.services.impl.WorkServiceImpl;
import courseproject.javacheck.utils.parseUtils.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Main parser for parsing java projects
 */
public class JavaProjectParser {
    private final Set<String> invocations;
    private final Set<String> allNames;
    private JavaParser parser;
    private final Work work;
    private final WorkServiceImpl service;

    public JavaProjectParser(Work work, WorkServiceImpl service) {
        this.work = work;
        this.service = service;
        invocations = new HashSet<>();
        allNames = new HashSet<>();
        initParsers();
    }

    /**
     * Initiate parsers for parsing project files and resolve method invocations
     */
    private void initParsers() {
        List<Path> javaDirectories = FilesUtil.getAllJavaDirectories(Paths.get(work.getLocalPath()));

        parser = new JavaParser();
        CombinedTypeSolver solver = new CombinedTypeSolver();
        solver.add(new ReflectionTypeSolver());
        javaDirectories.forEach(dir -> solver.add(new JavaParserTypeSolver(dir.toFile())));
        JavaSymbolSolver javaSolver = new JavaSymbolSolver(solver);
        StaticJavaParser.getConfiguration().setSymbolResolver(javaSolver);
    }

    /**
     * Parse java project represented by Work object
     * @return Parsed project represented by Work object
     */
    public synchronized Work checkWork() {
        String folderPathString = work.getLocalPath();

        List<Path> allJavaFiles = FilesUtil.getAllJavaFiles(Paths.get(folderPathString));

        StringBuilder reportStr = new StringBuilder();
        for (Path path : allJavaFiles) {
            reportStr.append(parseFile(path.toFile(), folderPathString));
            resolveMethodCalls(path.toFile());
        }

        //Structure
        work.setStructure(AntiPlagiarismByStructure.getStructure(invocations));

        //All names
        StringBuilder namesStr = new StringBuilder();
        allNames.forEach(name -> namesStr.append(name).append("\n"));
        work.setAllNames(namesStr.toString());

        //Plagiarism by structure
        String psReport = "\nFind class-structure isomorphism with the following works:" +
                AntiPlagiarismByStructure.getPlagiarismRangeByStructure(invocations, service.getAllWorks());
        reportStr.insert(0, psReport);

        //Plagiarism by names
        String pnReport = "\nFound matches on variable names with the following works:" +
                AntiPlagiarismByNames.getPlagiarismRangeByNames(allNames, service,0.5);
        reportStr.insert(0, pnReport);

        //Full name
        reportStr.insert(0,work.getFullName() + "\n-------------------------------\n");

        work.setReport(reportStr.toString());

        return work;
    }

    /**
     * Parse .java file
     * @param file Java file
     * @param root Root directory
     * @return Result of parsing
     */
    private String parseFile(File file, String root) {
        try {
            //Parser
            ParseResult<CompilationUnit> result = parser.parse(file);
            CompilationUnit cu = result.getResult().orElse(null);

            //Full file name
            StringBuilder sb = new StringBuilder();
            String fullFileName = file.getAbsolutePath().replace(root + "\\","");
            sb.append("\n-------------------------------\nFile: ").append(fullFileName);

            //All names
            VoidVisitor<Set<String>> nameCollector = new NameCollector();
            nameCollector.visit(cu, allNames);

            //INFO - need to be modified
            List<String> classInfo = new ArrayList<>();
            VoidVisitor<List<String>> classCollector = new ClassCollector();
            classCollector.visit(cu, classInfo);
            classInfo.forEach(sb::append);

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Resolve all method calls in .java file
     * @param file Java file
     */
    private void resolveMethodCalls(File file) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(file);
            cu.findAll(ClassOrInterfaceDeclaration.class).get(0).getFullyQualifiedName()
                    .ifPresent(name -> cu.findAll(MethodCallExpr.class)
                            .forEach(mce -> {
                                String mceName = mce.resolve().getQualifiedName();
                                mceName = mceName.substring(0, mceName.lastIndexOf('.'));
                                if (!name.equals(mceName))
                                    invocations.add(name + " > " + mceName);
                            }));
        } catch (UnsolvedSymbolException e) {
            int index = e.getName().indexOf('.');
            if (!e.getName().contains(" ")) {
                if (index != -1)
                    invocations.add("? > " + e.getName().substring(0, index));
                else
                    if (e.getName().charAt(0) > 'A' && e.getName().charAt(0) < 'Z')
                        invocations.add("? > " + e.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
