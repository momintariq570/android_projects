package com.example.momintariq.reportcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create students
        Student s1 = new Student("Momin Tariq");
        Student s2 = new Student("Tariq Momin");

        // Create courses for all students
        Course s1c1 = new Course("Teacher 1", "History");
        Course s1c2 = new Course("Teacher 2", "Math");
        Course s1c3 = new Course("Teacher 3", "Science");

        Course s2c1 = new Course("Teacher 1", "History");
        Course s2c2 = new Course("Teacher 2", "Math");
        Course s2c3 = new Course("Teacher 3", "Science");

        // Create report cards
        ReportCard s1ReportCard = new ReportCard(s1.getStudent());
        ReportCard s2ReportCard = new ReportCard(s2.getStudent());

        // Populate the report cards
        s1ReportCard.addToReportCard(s1c1, new Grade("A"));
        s1ReportCard.addToReportCard(s1c2, new Grade("B"));
        s1ReportCard.addToReportCard(s1c3, new Grade("A"));

        s2ReportCard.addToReportCard(s2c1, new Grade("A"));
        s2ReportCard.addToReportCard(s2c2, new Grade("A"));
        s2ReportCard.addToReportCard(s2c3, new Grade("A"));

        // Print report cards as logs
        Log.v("Student 1", s1ReportCard.toString());
        Log.v("Student 2", s2ReportCard.toString());
    }
}
