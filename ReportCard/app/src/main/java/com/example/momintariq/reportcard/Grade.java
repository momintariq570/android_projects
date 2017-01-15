package com.example.momintariq.reportcard;

/**
 * Created by momintariq on 12/20/16.
 */

public class Grade {

    // Instance variable to store a letter grade
    private String grade = "";

    // Constructor
    public Grade(String studentGrade) {
        this.grade = studentGrade;
    }

    // Getter for the grade
    public String getGrade() {
        return grade;
    }
}
