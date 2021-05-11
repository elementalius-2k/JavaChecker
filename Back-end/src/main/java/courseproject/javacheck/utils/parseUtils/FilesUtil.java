package courseproject.javacheck.utils.parseUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility for JavaProjectParser to find java files
 */
public class FilesUtil {
    /**
     * Find all .java files from root directory
     * @param root Root directory to search for .java files
     * @return All .java file paths
     */
    public static List<Path> getAllJavaFiles(Path root) {
        List<Path> result = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(root)) {
            result = paths.filter(Files::isRegularFile)
                          .filter(p -> p.getFileName().toString().endsWith(".java"))
                          .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.removeIf(p -> p.toAbsolutePath().toString().contains(".mvn"));
        return result;
    }

    /**
     * Find all java directories from root directory
     * @param root Root directory to search for java directories
     * @return All java directories paths
     */
    public static List<Path> getAllJavaDirectories(Path root) {
        List<Path> result = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(root)) {
            result = paths.filter(Files::isDirectory)
                          .filter(p -> p.getFileName().toString().equals("java"))
                          .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
