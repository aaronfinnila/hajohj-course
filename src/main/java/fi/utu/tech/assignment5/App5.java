package fi.utu.tech.assignment5;

import java.util.ArrayList;
import java.util.List;

import fi.utu.tech.common.GradingTask;
import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.TaskAllocator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App5 {
    public static void main( String[] args ) {

        long startTime = System.currentTimeMillis();
        
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.STATIC);
        List<Submission> gradedSubmissions = new ArrayList<>();
        List<Thread> gradingThreads = new ArrayList<>();
        
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        TaskAllocator allocator = new TaskAllocator();
        List<GradingTask> gradingTasks = allocator.allocate(ungradedSubmissions, 10);

        int gradingTaskAmount = gradingTasks.size();

        for (int i = 0; i < gradingTaskAmount; i++) {
            GradingTask gt = gradingTasks.get(i);
            Thread gradingThread = new Thread(gt);
            gradingThreads.add(gradingThread);
            System.out.println(gradingThread.getState());
        }

        for (Thread t : gradingThreads) {
            t.start();
            System.out.println(t.getState());
        }

        for (Thread t : gradingThreads) {
            try {
                t.join();
                System.out.println(t.getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (GradingTask gt : gradingTasks) {
            for (var gradedTask : gt.getGraded()) {
                gradedSubmissions.add(gradedTask);
            }
        }
        
        System.out.println("------------ CUT HERE ------------");
        for (var gs : gradedSubmissions) {
            System.out.println(gs);
        }

        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis()-startTime);
    }
}
