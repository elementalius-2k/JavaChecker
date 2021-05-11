package courseproject.javacheck.utils.parseUtils;

import courseproject.javacheck.services.impl.WorkServiceImpl;

import java.util.*;

public class AntiPlagiarismByNames {
    /**
     * Check work for plagiarism by variables names
     * @param names Collection of names to check work for plagiarism
     * @param service Elasticsearch work service
     * @param level The level of coincidence of two works, above which the work will be considered as plagiarism
     * @return String with full names and match levels of plagiarism works
     */
    public static String getPlagiarismRangeByNames(Collection<String> names, WorkServiceImpl service, double level) {
        Map<String, Integer> plagiarismWorks = new HashMap<>();
        double plagiarismLevel = names.size() * level;

        names.forEach(name -> {
            List<String> coincidences = service.getAllWorksNamesByNamesContains(name);
            coincidences.forEach(coincidence -> {
                if (!plagiarismWorks.containsKey(coincidence))
                    plagiarismWorks.put(coincidence, 1);
                else
                    plagiarismWorks.put(coincidence, plagiarismWorks.get(coincidence) + 1);
            });
        });

        StringBuilder sb = new StringBuilder();
        plagiarismWorks.entrySet().stream()
                .filter(entry -> entry.getValue() >= plagiarismLevel)
                .forEach(entry -> sb.append("\n").append(entry.getKey()).append(" - ")
                        .append(100 - entry.getValue() / names.size() * 100).append("%"));

        return sb.toString();
    }
}
