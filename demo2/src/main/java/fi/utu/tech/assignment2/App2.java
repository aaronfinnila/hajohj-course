package fi.utu.tech.assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import fi.utu.tech.assignment2.SubmissionGenerator.Strategy;


public class App2 {

    public static void main(String[] args) {
        BlockingQueue<Submission> gradedSubmissions = new ArrayBlockingQueue<>(60);
        
        List<AutomaticGrader> autograders = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            var ungradedSubmissions = SubmissionGenerator.generateSubmissions(10, 2000, Strategy.RANDOM);
            autograders.add(new AutomaticGrader(ungradedSubmissions, gradedSubmissions));
        }

        List<StudyRecord> studyRegistry = new ArrayList<>();
        StudyRegistrar studyRegistrar = new StudyRegistrar(gradedSubmissions, studyRegistry, "DTEK2088");
        
        for (var grader : autograders) {
            grader.start();
        }
        
        studyRegistrar.start();
        
        for (var grader : autograders) {
            try {
                grader.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Informoidaan kirjuria, että uusia palautuksia ei pitäisi enää tulla
        // Huomaa, että jossain tapauksissa kirjuri ei välttämättä kerkeä käsittelemään
        // kaikkia jonoon lisättyjä töitä ennen interruptia.
        // Tätä ongelmaa ei tarvitse kuitenkaan työssä ratkaista, mutta voit ottaa sen
        // vapaaehtoiseksi lisätehtäväksi
        studyRegistrar.interrupt();
        System.out.println(studyRegistrar.finalGrades.size());
    }
}
