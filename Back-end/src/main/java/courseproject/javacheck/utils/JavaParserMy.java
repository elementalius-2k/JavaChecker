package courseproject.javacheck.utils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class JavaParserMy {

    public static void func(String folderPathString) throws FileNotFoundException {

        JavaParser parser = new JavaParser();

//        String userTaskFolderPath = "de5135fe-71e8-49d8-8b45-e800b4231e55";
//        Path folderPath = Paths.get("/gitDownloadForTaskChecker/" + userTaskFolderPath);


        Path folderPath = Paths.get(folderPathString);


        getAllJavaFiles(folderPath).forEach(curr -> {
            parseFile(curr);
        });
        System.out.print("----------------------------");


//        File file = new File("/gitDownloadForTaskChecker/de5135fe-71e8-49d8-8b45-e800b4231e55/src/Data.java");
//        ParseResult<CompilationUnit> result = parser.parse(file);
//
//        CompilationUnit cu = result.getResult().get();
////        cu.getChildNodes().forEach(n -> System.out.print("NODE _ " + n.getMetaModel() + "\n"));
//
//
//        List<String> classNames = new ArrayList<>();
//        VoidVisitor<List<String>> classNameCollector = new ClassNameCollector();
//        classNameCollector.visit(cu, classNames);
//
//        List<String> methodNames = new ArrayList<>();
//        VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
//        methodNameCollector.visit(cu, methodNames);
//
//        System.out.println("Classes");
//        for (String str : classNames) {
//            System.out.println(str);
//        }
//
//        System.out.println("Methods");
//        for (String str : methodNames) {
//            System.out.println(str);
//        }


    }

    public static class ClassNameCollector extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(ClassOrInterfaceDeclaration cORid, List<String> collector) {
            super.visit(cORid, collector);
            collector.add(cORid.getNameAsString());
        }
    }


    private static class MethodNameCollector extends VoidVisitorAdapter<List<String>> {

        @Override
        public void visit(MethodDeclaration md, List<String> collector) {
            super.visit(md, collector);
            collector.add(md.getNameAsString());
        }
    }

    private static Set<File> getAllJavaFiles(Path path) {
        Set<File> result = new HashSet<>();
        try {
            Stream<Path> paths = Files.walk(path);

            paths.forEach(currPath -> {
                if (!currPath.equals(path)) {
                    result.addAll(getAllJavaFiles(currPath));
                } else {
                    if (isJavaFile(currPath)) {
                        result.add(currPath.toFile());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static boolean isJavaFile(Path currPath) {
        File currFile = currPath.toFile();
        int lastIndexOfDot = currFile.getName().lastIndexOf('.');
        String fileExtension = currFile.getName().substring(lastIndexOfDot + 1);

        return fileExtension.equals("java");
    }

    private static void parseFile(File file) {
        try {
            JavaParser parser = new JavaParser();
            ParseResult<CompilationUnit> result = parser.parse(file);

            CompilationUnit cu = result.getResult().get();

            List<String> classNames = new ArrayList<>();
            VoidVisitor<List<String>> classNameCollector = new ClassNameCollector();
            classNameCollector.visit(cu, classNames);

            List<String> methodNames = new ArrayList<>();
            VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
            methodNameCollector.visit(cu, methodNames);

            System.out.println("File - " + file.getName());

            System.out.println("Classes");
            for (String str : classNames) {
                System.out.println(str);
            }

            System.out.println("Methods");
            for (String str : methodNames) {
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
