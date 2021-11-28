package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;


@RestController
public class StudentController {
    public static ArrayList<Student> readfile(String filename){
        ArrayList studentList = new ArrayList();
        try{
            FileReader file = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(file);
            String firstLine = bufferedReader.readLine();
            String line;

            while ((line = bufferedReader.readLine()) != null){
                String[] tk = line.split(",");
                Student st = new Student(Integer.parseInt(tk[0]),tk[1],Double.parseDouble(tk[2]),tk[3],tk[4]);
                studentList.add(st);
                System.out.println(studentList);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  studentList;
    }
    ArrayList<Student> stu = readfile("Student.txt");

    @GetMapping
    public ArrayList<Student> Welcome(){
        return stu;
    }

    @GetMapping("/name/{name}")
    public  ArrayList<Student> searchName(@PathVariable String name) {
        String stuName = name.trim();

        ArrayList<Student> stuList = new ArrayList<>();
        if(!stu.isEmpty()){
            for(int i = 0; i< stu.size();i++){
                if(stuName.trim().equalsIgnoreCase(stu.get(i).getFirstName())){
                    stuList.add(stu.get(i));
                }
            }
        }
        return stuList;
    }

    @GetMapping("/student")
    public  ArrayList<Student> searchByGPAandGender(@RequestParam double gpa, @RequestParam String gender) {
        System.out.println("Search by gpa " + gpa + " gender " +gender);
        ArrayList<Student> studentList = readfile("Student.txt");
        ArrayList<Student> studentListFound = new ArrayList<>();
        for(Student student : studentList )
        {
            if(student.getGpa() == gpa & student.getGender().equalsIgnoreCase(gender.trim()))
            {
                studentListFound.add(student);
            }
        }

        return studentListFound;

    }
    @GetMapping("/gpa")
    public double getAverageGPA() throws IOException {
        ArrayList<Student> studentList = readfile("Student.txt");
        double totalGPA = 0 ;
        for(Student student : studentList )
        {
            totalGPA+=student.getGpa();
        }
        return totalGPA/studentList.size();
    }
}
