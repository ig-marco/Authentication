package com.example.studentcommunicationportal;

public class Student {
    public String name,rollno,email,phone,college,branch,year,gender;

    public Student(){

    }

    public Student( String name,
                    String rollno,
                    String email,
                    String phone,
                    String college,
                    String branch,
                    String year,
                    String gender) {
        this.name = name;
        this.rollno = rollno;
        this.email = email;
        this.phone = phone;
        this.college = college;
        this.branch = branch;
        this.year = year;
        this.gender = gender;
    }
}
