package courseproject.javacheck.service.impl;

import courseproject.javacheck.domain.Subject;
import courseproject.javacheck.domain.User;
import courseproject.javacheck.repo.SubjectRepo;
import courseproject.javacheck.repo.UserRepo;
import courseproject.javacheck.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepo subjectRepo;
    private final UserRepo userRepo;

    public SubjectServiceImpl(SubjectRepo subjectRepo, UserRepo userRepo) {
        this.subjectRepo = subjectRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Subject createSubject(Subject subject) {
        try {
            subjectRepo.save(subject);
            return subject;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateSubject(Subject subject) {
        try {
            subjectRepo.findById(subject.getId()).ifPresent(subjectToChange -> {
                subjectToChange.setTeacher(subject.getTeacher());
                subjectToChange.setName(subject.getName());
                subjectToChange.setDescription(subject.getDescription());
                subjectRepo.save(subjectToChange);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteSubject(Integer id) {
        try {
            Optional<Subject> curr = subjectRepo.findById(id);
            curr.ifPresent(subjectRepo::delete);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Subject getSubjectById(Integer id) {
        return subjectRepo.findById(id).orElse(null);
    }

    @Override
    public List<Subject> getSubjectsByTeacher(Integer teacherId) {
        User user = userRepo.findById(teacherId).orElse(null);
        if (user != null)
            return subjectRepo.findAllByTeacher(user);
        return new ArrayList<>();
    }

    @Override
    public Subject getSubjectByName(String name) {
        return subjectRepo.findByName(name).orElse(null);
    }

    @Override
    public List<Subject> getAllSubjects() {
        return (List<Subject>) subjectRepo.findAll();
    }
}
