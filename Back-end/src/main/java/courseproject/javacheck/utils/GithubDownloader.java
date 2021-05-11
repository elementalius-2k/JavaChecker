package courseproject.javacheck.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GithubDownloader {
    private final static String rootPath = "../JavaCheckerProjects";

    /**
     * Clone Github repository from URL and save it to root path with specified name
     * @param githubUrl URL to Github repository
     * @param name Specified work name
     * @return Absolute path to saved work
     */
    public static String download(String githubUrl, String name) {
        Path path = Paths.get(rootPath + "/" + name);
        try {
            Git.cloneRepository()
                    .setURI(githubUrl)
                    .setDirectory(path.toFile())
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return null;
        }
        return path.toAbsolutePath().normalize().toString();
    }
}
