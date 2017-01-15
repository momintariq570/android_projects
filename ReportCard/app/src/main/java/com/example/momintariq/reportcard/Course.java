package com.example.momintariq.reportcard;

/**
 * Created by momintariq on 12/20/16.
 */

public class Course {

    // Instance variable to store the course and teacher's names
    private String teacherName = "";
    private String courseName = "";

    // Constructor
    public Course(String tName, String cName) {
        teacherName = tName;
        courseName = cName;
    }

    // Getter for the teacher's name
    public String getTeacherName() {
        return teacherName;
    }

    // Getter for the teacher's name
    public String getCourseName() {
        return courseName;
    }
}
