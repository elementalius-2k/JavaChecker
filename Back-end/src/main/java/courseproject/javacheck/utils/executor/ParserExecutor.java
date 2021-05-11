package courseproject.javacheck.utils.executor;

import courseproject.javacheck.model.postgresqlModels.StudentWork;
import courseproject.javacheck.services.StudentWorkService;
import courseproject.javacheck.services.WorkService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executor for checking students works in queue
 */
public class ParserExecutor {
    private static ParserExecutor instance;

    private final StudentWorkService studentWorkService;
    private final WorkService workService;
    private final ExecutorService executor;


    private ParserExecutor(StudentWorkService studentWorkService, WorkService workService) {
        this.studentWorkService = studentWorkService;
        this.workService = workService;
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Return existing or create new executor
     * @param studentWorkService Service to check
     * @param workService Service to check
     * @return Executor
     */
    public static ParserExecutor getInstance(StudentWorkService studentWorkService, WorkService workService) {
        if (instance == null)
            instance = new ParserExecutor(studentWorkService, workService);
        return instance;
    }

    /**
     * Add new work to queue to check
     * @param studentWork Work to check
     */
    public void execute(StudentWork studentWork) {
        ParserTask task = new ParserTask(studentWorkService, workService, studentWork);
        executor.submit(task);
    }
}
