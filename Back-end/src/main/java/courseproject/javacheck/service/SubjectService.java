package courseproject.javacheck.service;


import courseproject.javacheck.domain.Subject;
import courseproject.javacheck.repo.SubjectRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepo subjectRepo;

    public SubjectService(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    public boolean createSubject(Subject subject){
        try {
            subjectRepo.save(subject);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    public void updateSubject(Subject subject){
        subjectRepo.findById(subject.getId()).ifPresent(subjectToChange -> {
            subjectToChange.setName(subject.getName());
            subjectRepo.save(subjectToChange);
        });
    }

    public void deleteSubject(Integer id){
        Optional<Subject> curr = subjectRepo.findById(id);
        if(curr.isPresent()){
            subjectRepo.delete(curr.get());
        }
    }

    public Subject getSubjectById(Integer id){
        return subjectRepo.findById(id).get();
    }

    public List<Subject> getAllSubjects(){
        return (List<Subject>) subjectRepo.findAll();
    }

}
