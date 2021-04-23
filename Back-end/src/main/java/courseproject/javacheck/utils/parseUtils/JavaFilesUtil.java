package courseproject.javacheck.utils.parseUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class JavaFilesUtil {
    public static Set<File> getAllJavaFiles(Path path) {
        Set<File> result = new HashSet<>();
        try {
            Stream<Path> paths = Files.walk(path);
            paths.forEach(curr -> {
                if (!curr.equals(path))
                    result.addAll(getAllJavaFiles(curr));
                else
                    if (isJavaFile(curr))
                        result.add(curr.toFile());
                    else if (curr.toFile().isFile()) {
                        try {
                            Files.delete(curr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void deleteNotJavaFiles(Path path) {
        try {
            Stream<Path> paths = Files.walk(path);
            paths.forEach(curr -> {
                if (!curr.equals(path))
                    deleteNotJavaFiles(curr);
                else if (curr.toFile().isFile() && !isJavaFile(curr)) {
                    try {
                        Files.delete(curr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // Добавить удаление пустых директорий
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isJavaFile(Path path) {
        File currFile = path.toFile();
        int lastIndexOfDot = currFile.getName().lastIndexOf('.');
        if (lastIndexOfDot == -1)
            return false;
        String fileExtension = currFile.getName().substring(lastIndexOfDot + 1);
        return fileExtension.equals("java");
    }
}
