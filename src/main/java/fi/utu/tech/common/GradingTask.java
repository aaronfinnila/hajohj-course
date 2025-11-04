package fi.utu.tech.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GradingTask implements Runnable {

    private Random rnd = new Random();
    private List<Submission> submissions = new ArrayList<>();
    private List<Submission> graded = new ArrayList<>();

    public List<Submission> gradeAll(List<Submission> submissions) {
        List<Submission> graded = new ArrayList<>();
        for (var s : submissions) {
            graded.add(grade(s));
        }
        return graded;
    }

    public void setSubmissions(List<Submission> newSubmissions) {
        submissions = newSubmissions;
    }

    public void addSubmission(Submission submission) {
        submissions.add(submission);
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public List<Submission> getGraded() {
        return graded;
    }

    public Submission grade(Submission s) {
        try {
            Thread.sleep(s.getDifficulty());
        } catch (InterruptedException e) {
            System.err.println("Who dared to interrupt my sleep?!");
        }
        return s.grade(rnd.nextInt(6));
    }

    public void run() {
        for (var s : submissions) {
            graded.add(grade(s));
        }
    }
}
