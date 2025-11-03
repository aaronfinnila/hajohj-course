package fi.utu.tech.common;

import java.util.ArrayList;
import java.util.List;

public class TaskAllocator {

    public static List<GradingTask> sloppyAllocator(List<Submission> submissions) {
        List<GradingTask> tasks = new ArrayList<>();
        GradingTask task1 = new GradingTask();
        GradingTask task2 = new GradingTask();
        List<Submission> task1submissions = new ArrayList<>();
        List<Submission> task2submissions = new ArrayList<>();
        for (int i = 0; i < submissions.size(); i++) {
            if (i % 2 == 0) {
                task1submissions.add(submissions.get(i));
            } else {
                task2submissions.add(submissions.get(i));
            }
        }

        task1.setSubmissions(task1submissions);
        task2.setSubmissions(task2submissions);
        tasks.add(task1);
        tasks.add(task2);

        return tasks;
    }
    
    public static List<GradingTask> allocate(List<Submission> submissions, int taskCount) {
        List<GradingTask> tasks = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            GradingTask task = new GradingTask();
            tasks.add(task);
        }

        int index = 0;
        int submPerTask = submissions.size() / taskCount;

        for (GradingTask task : tasks) {
            List<Submission> submissionsForTask = new ArrayList<>();
            for (int i = 0; i < submPerTask; i++) {
                Submission subm = submissions.get(i+index);
                submissionsForTask.add(subm);
            }
            task.setSubmissions(submissionsForTask);
            index += submPerTask;
        }
        return tasks;
    }
}
