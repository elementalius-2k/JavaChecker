package courseproject.javacheck.services;

import courseproject.javacheck.model.postgresqlModels.Subject;

import java.util.List;

public interface SubjectService {
    Subject createSubject(Subject subject);
    boolean updateSubject(Subject subject);
    boolean deleteSubject(Integer id);
    Subject getSubjectById(Integer id);
    List<Subject> getSubjectsByTeacher(Integer teacherId);
    Subject getSubjectByName(String name);
    List<Subject> getAllSubjects();
}
