package com.example.momintariq.reportcard;

import java.util.HashMap;

/**
 * Created by momintariq on 12/20/16.
 */

public class ReportCard {

    // Instance variables to store student name and a data structure composed of courses and grades
    private Student studentName;
    private HashMap<Course, Grade> studentCourses;

    // Constructor
    public ReportCard(String name) {
        studentName = new Student(name);
        studentCourses = new HashMap<Course, Grade>();
    }

    // Setter for the hashmap
    public void addToReportCard(Course course, Grade grade) {
        studentCourses.put(course, grade);
    }

    public HashMap<Course, Grade> getStudentCourses() {
        return studentCourses;
    }

    // Compute the student's GPA
    public double computeGPA() {
        int sum = 0;
        int numOfCourses = studentCourses.size();

        for(Course c : studentCourses.keySet()) {
            if (studentCourses.get(c).getGrade() == "A") {
                sum += 4;
            } else if (studentCourses.get(c).getGrade() == "B") {
                sum += 3;
            } else if (studentCourses.get(c).getGrade() == "C") {
                sum += 2;
            } else if (studentCourses.get(c).getGrade() == "D") {
                sum += 1;
            } else {
                sum += 0;
            }
        }

        return (double) sum / numOfCourses;
    }

    // Prints the report card
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("Name: "+ studentName.getStudent() + "\n");

        for(Course c : studentCourses.keySet()) {
            sBuilder.append(c.getCourseName() + "\t" + c.getTeacherName() + "\t" +
                    studentCourses.get(c).getGrade() + "\n");
        }

        sBuilder.append("GPA: " + computeGPA());

        return sBuilder.toString();
    }
}
