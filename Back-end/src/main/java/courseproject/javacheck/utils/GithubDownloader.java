package courseproject.javacheck.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class GithubDownloader {
    private final static String path = "../JavaCheckerProjects";

    public GithubDownloader() {}

    public String download(String githubUrl, String name) {
        File file = new File(path + "/" + name);
        try {
            Git.cloneRepository()
                    .setURI(githubUrl)
                    .setDirectory(file)
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return null;
        }

        return file.getAbsolutePath();
    }
}
