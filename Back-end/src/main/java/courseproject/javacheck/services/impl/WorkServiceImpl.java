package courseproject.javacheck.services.impl;

import courseproject.javacheck.model.postgresqlModels.StudentWork;
import courseproject.javacheck.model.elasticsearchModels.Work;
import courseproject.javacheck.repositories.elasticsearchRepos.WorkRepo;
import courseproject.javacheck.services.WorkService;
import courseproject.javacheck.utils.JavaProjectParser;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    private final WorkRepo workRepo;

    public WorkServiceImpl(WorkRepo workRepo) {
        this.workRepo = workRepo;
    }

    public Work createWork(StudentWork studentWork) {
        try {
            String fullName = "User " + studentWork.getUser().getFullName() + "; Task " +
                    studentWork.getTask().getName() + "; Upload at " + studentWork.getDateTime().toString();
            Work work = new Work(studentWork.getId(), fullName,null,null,null, studentWork.getLocalPath());
            work = checkProject(work);
            workRepo.save(work);
            return work;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateWork(Work work) {
        try {
            workRepo.findById(work.getId()).ifPresent(workToChange -> {
                workToChange.setWorkId(work.getWorkId());
                workToChange.setReport(work.getReport());
                workToChange.setFullName(work.getFullName());
                workToChange.setStructure(work.getStructure());
                workToChange.setAllNames(work.getAllNames());
                workRepo.save(workToChange);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteWork(String id) {
        try {
            workRepo.findById(id).ifPresent(workRepo::delete);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAllWorks() {
        try {
            workRepo.deleteAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Work getWorkById(String id) {
        return workRepo.findById(id).orElse(null);
    }

    public Work getWorkByStudentWorkId(Integer id) {
        return workRepo.findByWorkId(id);
    }

    public List<Work> getAllWorks() {
        Page<Work> page = (Page<Work>) workRepo.findAll();
        return page.toList();
    }

    public List<String> getAllWorksNamesByNamesContains(String element) {
        List<Work> works = workRepo.findAllByAllNamesContains(element);
        List<String> names = new ArrayList<>();
        works.forEach(work -> names.add(work.getFullName()));
        return names;
    }

    private Work checkProject(Work work) {
        JavaProjectParser parser = new JavaProjectParser(work,this);
        return parser.checkWork();
    }
}
