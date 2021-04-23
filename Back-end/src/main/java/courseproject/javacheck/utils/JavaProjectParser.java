package courseproject.javacheck.utils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import courseproject.javacheck.utils.parseUtils.ClassCollector;
import courseproject.javacheck.utils.parseUtils.JavaFilesUtil;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JavaProjectParser {
    public static String startCheck(String folderPathString) {
        Path folderPath = Paths.get(folderPathString);
        File file = new File(folderPathString + "/report.txt");

        StringBuilder sb = new StringBuilder();

        JavaFilesUtil.getAllJavaFiles(folderPath).forEach(curr -> sb.append(parseFile(curr, folderPathString)));
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

    private static String parseFile(File file, String root) {
        try {
            JavaParser parser = new JavaParser();
            ParseResult<CompilationUnit> result = parser.parse(file);

            CompilationUnit cu = result.getResult().orElse(null);

            List<String> classInfo = new ArrayList<>();
            VoidVisitor<List<String>> classCollector = new ClassCollector();
            classCollector.visit(cu, classInfo);

            StringBuilder sb = new StringBuilder();
            String fullFileName = file.getAbsolutePath().replace(root,"");
            sb.append("\n-------------------------------\nFile: ").append(fullFileName);
            for (String info : classInfo)
                sb.append(info);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
