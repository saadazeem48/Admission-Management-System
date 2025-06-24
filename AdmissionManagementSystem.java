import java.util.ArrayList;
import java.util.Scanner;

class Applicant {
    int applicationNumber;
    String applicantName, guardianName;
    int matriculationMarks, intermediateMarks;
    int testScore;
    int interviewScore;
    int rollNumber;
    double overallScore;

    public Applicant(int applicationNumber, String applicantName, String guardianName, int matriculationMarks,
            int intermediateMarks, int testScore, int interviewScore, int registrationNumber) {
        this.applicationNumber = applicationNumber;
        this.applicantName = applicantName;
        this.guardianName = guardianName;
        this.matriculationMarks = matriculationMarks;
        this.intermediateMarks = intermediateMarks;
        this.testScore = testScore;
        this.interviewScore = interviewScore;
        this.rollNumber = rollNumber;
    }

    public double calculateTotalScore() {
        return (matriculationMarks * 10 / 1100) + (intermediateMarks * 50 / 1100) + (testScore * 30 / 50)
                + (interviewScore * 10 / 20);
    }
}

class EligibilityChecker {
    Scanner scanner = new Scanner(System.in);

    public void enterTestScores(ArrayList<Applicant> applicants) {
        System.out.print("Please enter the form number of the applicant to update the test score: ");
        int applicationNumber = scanner.nextInt();
        boolean found = false;

        for (Applicant applicant : applicants) {
            if (applicant.applicationNumber == applicationNumber) {
                System.out.print("Now please, enter the test score: ");
                int testScore = scanner.nextInt();
                applicant.testScore = testScore;
                System.out.println("Test score has been updated successfully for: " + applicant.applicantName);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Applicant with this form number " + applicationNumber + " not found.");
        }
    }

    public void displayEligibleApplicants(ArrayList<Applicant> applicants) {
        System.out.println("Eligible applicants for entry test:");
        for (Applicant applicant : applicants) {
            System.out.println(
                    "Roll Number: " + applicant.rollNumber + " Name: " + applicant.applicantName);
        }
    }
}

class MeritCalculator {
    public void generateMeritList(ArrayList<Applicant> applicants) {
        int size = applicants.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (applicants.get(j).calculateTotalScore() < applicants.get(j + 1).calculateTotalScore()) {
                    Applicant temp = applicants.get(j);
                    applicants.set(j, applicants.get(j + 1));
                    applicants.set(j + 1, temp);
                }
            }
        }

        System.out.println("Merit List:");
        for (int i = 0; i < Math.min(2, applicants.size()); i++) {
            Applicant applicant = applicants.get(i);
            System.out.println((i + 1) + ". Registration Number: " + applicant.rollNumber + " Name: "
                    + applicant.applicantName + " Total Score: " + applicant.calculateTotalScore());
        }
    }
}

public class AdmissionManagementSystem {
    ArrayList<Applicant> applicants = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    int registrationNumber = 101;

    public void enterApplication() {
        System.out.print("Please enter application number: ");
        int applicationNumber = scanner.nextInt();
        scanner.nextLine();

        for (Applicant applicant : applicants) {
            if (applicant.applicationNumber == applicationNumber) {
                System.out.println("Application number already exists. Please enter a unique application number.");
                return;
            }
        }

        System.out.print("Please enter name of the student: ");
        String applicantName = scanner.nextLine();
        System.out.print("Please enter guardian's name of the student: ");
        String guardianName = scanner.nextLine();
        System.out.print("Please enter matriculation marks obtained by the student: ");
        int matriculationMarks = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Please enter intermediate marks obtained by the student: ");
        int intermediateMarks = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Please enter the test score: ");
        int testScore = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Please enter the interview score: ");
        int interviewScore = scanner.nextInt();
        scanner.nextLine();

        // Check eligibility based on intermediate marks
        if (intermediateMarks >= 550 && intermediateMarks <= 1100) {
            Applicant applicant = new Applicant(applicationNumber, applicantName, guardianName, matriculationMarks,
                    intermediateMarks, testScore, interviewScore, registrationNumber);
            applicants.add(applicant);
            System.out.println("Application has been submitted successfully");
            System.out.println("You are eligible for the entry test");
            System.out.println("Your Registration Number is: " + registrationNumber++);
        } else {
            System.out.println("Application is rejected");
            System.out.println("You are not eligible for the entry test");
        }
    }
}

class Driver {
    public static void main(String[] args) {
        AdmissionManagementSystem admissionSystem = new AdmissionManagementSystem();
        EligibilityChecker eligibilityChecker = new EligibilityChecker();
        MeritCalculator meritCalculator = new MeritCalculator();

        while (true) {
            System.out.println("BSCS Admission Management System");
            System.out.println("1. Enter applicant form number");
            System.out.println("2. Enter test scores");
            System.out.println("3. Show the list of eligible applicants");
            System.out.println("4. Show merit list");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    admissionSystem.enterApplication();
                    break;
                case 2:
                    eligibilityChecker.enterTestScores(admissionSystem.applicants);
                    break;
                case 3:
                    eligibilityChecker.displayEligibleApplicants(admissionSystem.applicants);
                    break;
                case 4:
                    meritCalculator.generateMeritList(admissionSystem.applicants);
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }
}
