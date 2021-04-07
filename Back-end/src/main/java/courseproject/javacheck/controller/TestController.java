package courseproject.javacheck.controller;

import courseproject.javacheck.utils.GithubDownloader;
import courseproject.javacheck.utils.JavaParserMy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.HashMap;

@RestController
public class TestController {

    private final GithubDownloader ghd;

    public TestController(GithubDownloader ghd) {
        this.ghd = ghd;
    }

    @PostMapping("/download-git-repository")
    public HashMap<String, String> test(@RequestParam(value = "url") String githubUrl) {


        String result = ghd.download(githubUrl);

        if (result.isEmpty()) {
            return new HashMap<String, String>() {{
                put("result", "error");
            }};
        } else {
            return new HashMap<String, String>() {{
                put("result", "success");
            }};
        }
    }


    @GetMapping("/p")
    public String p() throws FileNotFoundException {


        JavaParserMy.func(null);
        return "test";
    }
}
