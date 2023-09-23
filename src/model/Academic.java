/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MSII
 */

public class Academic {
    private String id;
    private int semester;
    private String course;

    public Academic() {
    }
    
    public Academic(String id, int semester, String course) {
        this.id = id;
        this.semester = semester;
        this.course = course;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    
    
    public String getId() {
        return id;
    }

    public int getSemester() {
        return semester;
    }

    public String getCourse() {
        return course;
    }
    
    
}
