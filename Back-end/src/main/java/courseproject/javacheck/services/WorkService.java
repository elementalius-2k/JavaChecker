package courseproject.javacheck.services;

import courseproject.javacheck.model.elasticsearchModels.Work;
import courseproject.javacheck.model.postgresqlModels.StudentWork;

import java.util.List;

public interface WorkService {
    Work createWork(StudentWork studentWork);
    boolean updateWork(Work work);
    boolean deleteWork(String id);
    boolean deleteAllWorks();
    Work getWorkById(String id);
    Work getWorkByStudentWorkId(Integer id);
    List<Work> getAllWorks();
    List<String> getAllWorksNamesByNamesContains(String element);
}
