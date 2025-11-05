package fi.utu.tech.assignment6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import fi.utu.tech.common.GradingTask;
import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App6 {
    public static void main( String[] args ) {

        long startTime = System.currentTimeMillis();

        int submissionAmount = 60;
        
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(submissionAmount, 200, Strategy.UNFAIR);
        List<Submission> gradedSubmissions = new ArrayList<>();
        
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        List<GradingTask> gradingTasks = new ArrayList<>();

        for (int i = 0; i < submissionAmount; i++) {
            GradingTask gTask = new GradingTask();
            gTask.addSubmission(ungradedSubmissions.get(i));
            gradingTasks.add(gTask);
        }

        ExecutorService exeService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (var gt : gradingTasks) {
            exeService.execute(gt);
        }

        exeService.shutdown();

        try {
            exeService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (exeService.isTerminated()) {
            for (var gt : gradingTasks) {
                for (var gradedSubm : gt.getGraded()) {
                    gradedSubmissions.add(gradedSubm);
                }
            }
        }
        
        System.out.println("------------ CUT HERE ------------");
        for (var gs : gradedSubmissions) {
            System.out.println(gs);
        }

        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis()-startTime);
    }
}
