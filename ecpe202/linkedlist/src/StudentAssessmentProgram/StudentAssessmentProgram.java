package StudentAssessmentProgram;

import java.util.Scanner;
import java.util.LinkedList;

public class StudentAssessmentProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> students = new LinkedList<>();
        LinkedList<Integer> grades = new LinkedList<>();

        System.out.println("Student Assessment Program");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add student");
            System.out.println("2. Display student list");
            System.out.println("3. Compute average score");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("\nEnter student name: ");
                    students.add(scanner.nextLine());

                    System.out.print("Enter student score: ");
                    grades.add(scanner.nextInt());

                    System.out.println("\nStudent added successfully!");
                    break;

                case 2:
                    if (!students.isEmpty()) {
                        System.out.println("\nStudent List:");
                        for (int i = 0; i < students.size(); i++) {
                            System.out.println("\tStudent: " + students.get(i));
                            System.out.println("\tScore: " + grades.get(i));
                        }
                    } else {
                        System.out.println("\nStudent List: Empty");
                    }
                    break;

                case 3:
                    if (!grades.isEmpty()) {
                        int total = 0;
                        for (int score : grades) {
                            total += score;
                        }
                        int average = total / grades.size();
                        System.out.println("\nAverage Score: " + average);
                    } else {
                        System.out.println("\nNo grades available to calculate average.");
                    }
                    break;

                case 4:
                    System.out.println("\nExiting Program");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid choice! Please try again.");
            }
        }
    }
}
