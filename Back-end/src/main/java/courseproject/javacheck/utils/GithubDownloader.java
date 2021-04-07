package courseproject.javacheck.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Component
public class GithubDownloader {

    private final static String path = "/gitDownloadForTaskChecker";

    public GithubDownloader(){
    }

    public String download(String githubUrl) {
        File file = new File(path + "/" + UUID.randomUUID());
        try {
            Git.cloneRepository()
                    .setURI(githubUrl)
                    .setDirectory(file)
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return "";
        }
        return file.getAbsolutePath();
    }

}
