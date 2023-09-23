/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;



import Validation.Utility;
import java.util.ArrayList;
import java.util.Collections;
import model.Academic;
import model.Student;
import view.Menu;

public class Management extends Menu<String> {
    private ArrayList<Student> sList;
    private ArrayList<Academic> aList;

    public Management(ArrayList<Student> sList, ArrayList<Academic> aList) {
        super("Management Menu", new String[]{"Create", "Find/Sort", "Update/Delete", "Report", "Good bye",});
        this.sList = sList;
        this.aList = aList;
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1:
                create();
                break;
            case 2:
                findAndSort();
                break;
            case 3:
                updateAndDelete();
                break;
            case 4:
                report();
                break;
            case 5:
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. End.");
        }
    }

    private void create() {
        String xId = Utility.GetString("Enter id: ", false);
        if (findID(xId) >= 0) {
            addAcademic(xId);
        } else {
            System.out.println("This is a New student!");
            String newStudentName = Utility.GetString("Enter student Name: ", false);
            sList.add(new Student(xId, newStudentName));
            addAcademic(xId);
        }

    
    }

    private void findAndSort() {
        int choice;

        System.out.println("1. Find by Name\n" + "2. Sort by Name\n");

        choice = Utility.GetInt("Enter your choice: ", 1, 2);
        switch (choice) {
            case 1:
                findName(Utility.GetString("Enter Name: ", false));
                break;
            case 2:
                Collections.sort(sList);
                System.out.println("The List has been sorted!");
                break;
        }
    }

    private void findName(String xName) {
        boolean isEmpty = true;

        System.out.format("%-15s%-15s%-15s%-15s%-15s\n", "ID", "Name", "Semester", "Course", "Total Course");
        for (int i = 0; i < sList.size(); ++i) {
            if (sList.get(i).getStudentName().toLowerCase().contains(xName.toLowerCase())) {
                isEmpty = false;
                String xId = sList.get(i).getId();
                String tempName = sList.get(i).getStudentName();
                ArrayList<Academic> tempList = reportById(xId);
                for (Academic Entry : tempList) {
                    int xTotalCourse = getTotalCourse(xId);
                    System.out.format("%-15s%-15s%-15d%-15s%-15d\n", xId, tempName, Entry.getSemester(),
                            Entry.getCourse(), xTotalCourse);

                }
            }
        }
        if (isEmpty == true) {
            System.out.println("Not found!");
        }
    }

    private void updateAndDelete() {
        int choice;
        System.out.println("1. Update\n" + "2. Delete\n");

        choice = Utility.GetInt("Enter your choice: ", 1, 2);
        switch (choice) {
            case 1:
                Update(Utility.GetString("Enter Id: ", false));
                break;
            case 2:
                Delete(Utility.GetString("Enter Id: ", false));
                break;
        }
    }

    private void Update(String xId) {
        int x = findID(xId);
        if (x < 0) {
            System.out.println("This Id doesn't exist! ");
            return;
        }

        int choice;
        System.out.println("1. Update name\n" + "2. Update Academic\n");

        choice = Utility.GetInt("Enter your choice: ", 1, 2);

        switch (choice) {
            case 1:
                System.out.format("%-15s%-15s\n", "ID", "Name");
                System.out.format("%-15s%-15s\n", xId, sList.get(x).getStudentName());

                sList.get(x).setStudentName(Utility.GetString("Enter new Name", false));
                System.out.println("Update successfully!");
                break;
            case 2:
                ArrayList<Academic> tempList = reportById(xId);
                if (tempList.size() == 0) {
                    System.out.println("Empty!");
                    return;
                } else {
                    System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s\n", "No", "ID", "Name", "Semester", "Course",
                            "Total Course");
                    for (int i = 0; i < tempList.size(); ++i) {
                        int xTotalCourse = getTotalCourse(xId);
                        System.out.format("%-15d%-15s%-15s%-15d%-15s%-15d\n", i + 1, xId, sList.get(x).getStudentName(),
                                tempList.get(i).getSemester(), tempList.get(i).getCourse(), xTotalCourse);
                    }
                    int positionInTempList = Utility.GetInt("Enter No: ", 1, tempList.size()) - 1;
                    int pos_In_aList = aList.indexOf(tempList.get(positionInTempList));

                    System.out.println("1. Update Semester\n" + "2. Update Course\n");

                    choice = Utility.GetInt("Enter your choice", 1, 2);
                    switch (choice) {
                        case 1:
                            int xSemester = Utility.GetInt("Enter semester: ", 1, Integer.MAX_VALUE);
                            if (findCourse(xId, xSemester, aList.get(pos_In_aList).getCourse()) >= 0) {
                                System.err.println("Duplicate Error!");
                            } else {
                                aList.get(pos_In_aList).setSemester(xSemester);
                                System.out.println("Updated Successfully");
                            }
                            break;
                        case 2:
                            String xCourse = Utility.getInArray(Utility.COURSES_LIST, "Enter Course");
                            if (findCourse(xId, aList.get(pos_In_aList).getSemester(), xCourse) >= 0) {
                                System.err.println("Duplicate Error!");
                            } else {
                                aList.get(pos_In_aList).setCourse(xCourse);
                                System.out.println("Updated Successfully");
                            }
                            break;
                    }
                }
                break;
        }
    }

    private void Delete(String xId) {
        int x = findID(xId);
        if (x < 0) {
            System.out.println("This Id doesn't exist! ");
            return;
        }

        int choice;
        System.out.println("1. Delete Student\n" + "2. Delete Academic Info\n");

        choice = Utility.GetInt("Enter your choice: ", 1, 2);

        switch (choice) {
            case 1:
                sList.remove(x);
                aList.removeAll(reportById(xId));
                System.out.println("The student has been removed!");
                break;
            case 2:
                ArrayList<Academic> tempList = reportById(xId);
                if (tempList.size() == 0) {
                    System.out.println("Empty!");
                    return;
                } else {
                    System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s\n", "No", "ID", "Name", "Semester", "Course",
                            "Total Course");
                    for (int i = 0; i < tempList.size(); ++i) {
                        int xTotalCourse = getTotalCourse(xId);
                        System.out.format("%-15d%-15s%-15s%-15d%-15s%-15d\n", i + 1, xId, sList.get(x).getStudentName(),
                                tempList.get(i).getSemester(), tempList.get(i).getCourse(), xTotalCourse);
                    }
                    int position_In_tempList = Utility.GetInt("Enter No: ", 1, tempList.size()) - 1;

                    aList.remove(tempList.get(position_In_tempList));
                    System.out.println("This academic info has been deleted!");
                }
                break;
        }
    }

    private void report() {
        System.out.format("%-15s%-15s%-15s%-15s%-15s\n", "ID", "Name", "Semester", "Course", "Total Course");
        for (Student student : sList) {
            ArrayList<Academic> tmp = reportById(student.getId());
            if (tmp.size() == 0) {
                System.out.format("%-15s%-15s%-15s%-15s%-15d\n", student.getId(), student.getStudentName(), "", "", 0);
            } else {
                for (Academic Entry : tmp) {
                    int xTotalCourse = getTotalCourse(student.getId());
                    System.out.format("%-15s%-15s%-15s%-15s%-15d\n", student.getId(), student.getStudentName(),
                            Entry.getSemester(), Entry.getCourse(), xTotalCourse);
                }
            }
        }
    }

    private ArrayList<Academic> reportById(String xId) {
        ArrayList<Academic> res = new ArrayList<Academic>();

        for (int i = 0; i < aList.size(); ++i) {
            if (aList.get(i).getId().equalsIgnoreCase(xId)) {
                res.add(aList.get(i));
            }
        }
        return res;
    }

    private int getTotalCourse(String xId) {
        int res = 0;
        for (int i = 0; i < aList.size(); ++i) {
            if (aList.get(i).getId().equalsIgnoreCase(xId))
                ++res;
        }
        return res;
    }

    private int findID(String xId) {
        for (int i = 0; i < sList.size(); ++i) {
            if (sList.get(i).getId().equalsIgnoreCase(xId))
                return i;
        }
        return -1;
    }

    private void addAcademic(String xId) {
        int xSemester = Utility.GetInt("Enter Semester: ", 1, Integer.MAX_VALUE);
        String xCourse = Utility.getInArray(Utility.COURSES_LIST, "Enter Course: ");
        if (findCourse(xId, xSemester, xCourse) >= 0) {
            System.out.println("This academic info has existed!");
        } else {
            aList.add(new Academic(xId, xSemester, xCourse));
        }
    }

    private int findCourse(String xId, int xSemester, String xCourse) {
        for (int i = 0; i < aList.size(); ++i) {
            if (aList.get(i).getId().equalsIgnoreCase(xId) && aList.get(i).getSemester() == xSemester
                    && aList.get(i).getCourse().equalsIgnoreCase(xCourse)) {
                return i;
            }
        }
        return -1;
    }
}

