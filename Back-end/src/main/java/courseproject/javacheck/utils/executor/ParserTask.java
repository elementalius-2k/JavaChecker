package courseproject.javacheck.utils.executor;

import courseproject.javacheck.model.elasticsearchModels.Work;
import courseproject.javacheck.model.postgresqlModels.StudentWork;
import courseproject.javacheck.services.StudentWorkService;
import courseproject.javacheck.services.WorkService;

import java.time.LocalDateTime;

public class ParserTask implements Runnable {
    private final StudentWorkService studentWorkService;
    private final WorkService workService;
    private StudentWork studentWork;

    public ParserTask(StudentWorkService studentWorkService, WorkService workService, StudentWork studentWork) {
        this.studentWorkService = studentWorkService;
        this.workService = workService;
        this.studentWork = studentWork;
    }

    @Override
    public void run() {
        try {
            if (studentWork == null)
                throw new Exception("Error creating student work");

            studentWork.setDateTime(LocalDateTime.now());
            String workName = studentWork.getUser().getId() + "_" + studentWork.getTask().getId()
                    + "_" + studentWork.getDateTime().toString().replaceAll("[:.]","-");
            String localPath = studentWorkService.downloadGithubRepository(studentWork.getPath(), workName);
            studentWork.setLocalPath(localPath);
            studentWork = studentWorkService.createStudentWork(studentWork);

            String fullName = "User " + studentWork.getUser().getFullName() + "; Task " +
                    studentWork.getTask().getName() + "; Upload at " + studentWork.getDateTime().toString();
            Work work = new Work(studentWork.getId(), fullName,null,null,null, studentWork.getLocalPath());
            work = workService.checkProject(work);
            if (work == null)
                throw new Exception("Error creating work or checking project");
            workService.createWork(work);

            studentWork.setSystemReview("The work was been checked. You can download the report");
            studentWorkService.updateStudentWork(studentWork);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
