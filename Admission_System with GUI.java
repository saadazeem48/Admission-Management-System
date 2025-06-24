import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

class Student {
    private int formNumber;
    private String name;
    private String fatherName;
    private int matricMarks;
    private int fScMarks;
    private int testMarks;
    private int interviewMarks;
    private int rollNo;

    public Student(int formNumber, String name, String fatherName, int matricMarks, int fScMarks, int testMarks,
            int interviewMarks, int rollNo) {
        this.formNumber = formNumber;
        this.name = name;
        this.fatherName = fatherName;
        this.matricMarks = matricMarks;
        this.fScMarks = fScMarks;
        this.testMarks = testMarks;
        this.interviewMarks = interviewMarks;
        this.rollNo = rollNo;
    }

    public void setFormNumber(int formNumber) {
        this.formNumber = formNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setMatricMarks(int matricMarks) {
        this.matricMarks = matricMarks;
    }

    public void setFScMarks(int fScMarks) {
        this.fScMarks = fScMarks;
    }

    public void setTestMarks(int testMarks) {
        this.testMarks = testMarks;
    }

    public void setInterviewMarks(int interviewMarks) {
        this.interviewMarks = interviewMarks;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public int getFormNumber() {
        return formNumber;
    }

    public String getName() {
        return name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public int getMatricMarks() {
        return matricMarks;
    }

    public int getFScMarks() {
        return fScMarks;
    }

    public int getTestMarks() {
        return testMarks;
    }

    public int getInterviewMarks() {
        return interviewMarks;
    }

    public int getRollNo() {
        return rollNo;
    }

    public double getTotalScore() {
        return (matricMarks * 10 / 1100.0) + (fScMarks * 50 / 1100.0) + (testMarks * 30 / 50.0)
                + (interviewMarks * 10 / 20.0);
    }
}

class FormEntry {
    private ArrayList<Student> students = new ArrayList<>();
    private int rollNo = 101;

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void formEntryGUI() {
        try {
            int formNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter form number:"));
            for (Student student : students) {
                if (student.getFormNumber() == formNumber) {
                    JOptionPane.showMessageDialog(null,
                            "Form number already exists. Please enter a unique form number.");
                    return;
                }
            }

            String name = JOptionPane.showInputDialog("Enter your name:");
            String fatherName = JOptionPane.showInputDialog("Enter father's name:");
            int matricMarks = Integer.parseInt(JOptionPane.showInputDialog("Enter matric marks:"));
            int fScMarks = Integer.parseInt(JOptionPane.showInputDialog("Enter FSc marks:"));
            int testMarks = Integer.parseInt(JOptionPane.showInputDialog("Enter test marks:"));
            int interviewMarks = Integer.parseInt(JOptionPane.showInputDialog("Enter interview marks:"));

            if (fScMarks >= 550 && fScMarks <= 1100) {
                Student student = new Student(formNumber, name, fatherName, matricMarks, fScMarks, testMarks,
                        interviewMarks, rollNo++);
                students.add(student);
                JOptionPane.showMessageDialog(null,
                        "Form submitted successfully!\nYou are eligible for the entry test.\nYour Roll Number is: "
                                + student.getRollNo());
            } else {
                JOptionPane.showMessageDialog(null, "Form rejected.\nYou are not eligible for the entry test.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
        }
    }
}

class Eligible {
    public void enterTestScoresGUI(ArrayList<Student> students) {
        try {
            int formNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter form number to update test score:"));
            for (Student student : students) {
                if (student.getFormNumber() == formNumber) {
                    int testScore = Integer.parseInt(JOptionPane.showInputDialog("Enter new test score:"));
                    student.setTestMarks(testScore);
                    JOptionPane.showMessageDialog(null, "Test score updated successfully for " + student.getName());
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Student with form number " + formNumber + " not found.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
        }
    }

    public void displayListGUI(ArrayList<Student> students) {
        StringBuilder eligibleList = new StringBuilder("Eligible Candidates for Entry Test:\n");
        for (Student student : students) {
            eligibleList.append("Roll Number: ").append(student.getRollNo()).append(", Name: ")
                    .append(student.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(null, eligibleList.toString());
    }
}

class MeritList {
    public void meritListGUI(ArrayList<Student> students) {
        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = 0; j < students.size() - 1 - i; j++) {
                if (students.get(j).getTotalScore() < students.get(j + 1).getTotalScore()) {
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
        String meritList = "Merit List:\n";
        DecimalFormat df = new DecimalFormat("#.##");

        for (int i = 0; i < Math.min(10, students.size()); i++) {
            Student student = students.get(i);
            meritList += (i + 1) + ". Roll Number: " + student.getRollNo() +
                    ", Name: " + student.getName() +
                    ", Total Score: " + df.format(student.getTotalScore()) + "\n";
        }

        JOptionPane.showMessageDialog(null, meritList);
    }
}

public class Admission_System {
    public static void main(String[] args) {
        FormEntry formEntry = new FormEntry();
        Eligible eligible = new Eligible();
        MeritList meritList = new MeritList();

        JFrame frame = new JFrame("BSCS Admission System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton btnEnterForm = new JButton("Enter Student Form");
        JButton btnUpdateTestScores = new JButton("Update Test Scores");
        JButton btnShowEligible = new JButton("Show Eligible Candidates");
        JButton btnShowMeritList = new JButton("Show Merit List");

        btnEnterForm.addActionListener(e -> formEntry.formEntryGUI());
        btnUpdateTestScores.addActionListener(e -> eligible.enterTestScoresGUI(formEntry.getStudents()));
        btnShowEligible.addActionListener(e -> eligible.displayListGUI(formEntry.getStudents()));
        btnShowMeritList.addActionListener(e -> meritList.meritListGUI(formEntry.getStudents()));

        panel.add(btnEnterForm);
        panel.add(btnUpdateTestScores);
        panel.add(btnShowEligible);
        panel.add(btnShowMeritList);
        frame.add(panel);
        frame.setVisible(true);
    }
}
