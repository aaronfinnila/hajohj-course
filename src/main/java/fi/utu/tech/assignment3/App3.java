package fi.utu.tech.assignment3;

import java.util.List;

import fi.utu.tech.common.GradingTask;
import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App3 {
    public static void main( String[] args ) {

        long startTime = System.currentTimeMillis();
        
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.STATIC);
        
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }
        
        GradingTask gradingTask = new GradingTask();
        Thread gradingThread = new Thread(gradingTask);

        gradingTask.setSubmissions(ungradedSubmissions);

        gradingThread.start();
        try {
            System.out.println(gradingThread.getState());
            gradingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }    
        
        System.out.println("------------ CUT HERE ------------");
        for (var gs : gradingTask.getGraded()) {
            System.out.println(gs);
        }
        
        System.out.println(gradingThread.getState());

        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis()-startTime);
    }
}
