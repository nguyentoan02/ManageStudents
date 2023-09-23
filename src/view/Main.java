/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.Management;
import java.util.ArrayList;
import model.Academic;
import model.Student;

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> studentList = new ArrayList<>();
        ArrayList<Academic> academicList = new ArrayList<>();

        // Tạo và chạy lớp Management
        Management management = new Management(studentList, academicList);
        management.run();
    }
}
