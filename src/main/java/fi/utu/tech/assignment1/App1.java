package fi.utu.tech.assignment1;

import java.util.List;

import fi.utu.tech.common.GradingTask;
import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App1 {
    public static void main( String[] args )
    {

        long startTime = System.currentTimeMillis();

        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.STATIC);

        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        GradingTask gradingTask = new GradingTask();

        gradingTask.setSubmissions(ungradedSubmissions);
        gradingTask.run();
        
        System.out.println("------------ CUT HERE ------------");
        for (var gs : gradingTask.getGraded()) {
            System.out.println(gs);
        }

        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis()-startTime);
    }
}
