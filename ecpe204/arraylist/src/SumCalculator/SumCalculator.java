package SumCalculator;

import java.util.Scanner;
import java.util.ArrayList;

public class SumCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.println("Sum Calculator");

        System.out.println("Enter integers (Enter a non-integer to finish):");
        int sum = 0;

        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                numbers.add(scanner.nextInt());
            } else {
                break;
            }
        }
        scanner.close();

        for (int num : numbers) {   
            sum += num;
        }

        System.out.println("\nSum of Integers: " + sum);
    }
}
