package CharacterInput;

import java.util.LinkedList;
import java.util.Scanner;

public class CharacterInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<Character> characters = new LinkedList<>();

        System.out.println("Character Input");

        System.out.println("\nEnter characters (Enter 'x' to finish):");

        while (true) {
            System.out.print("Enter a character: ");
            String input = scanner.next();
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                char inputChar = input.charAt(0);
                if (inputChar == 'x') {
                    break;
                }
                characters.add(inputChar);
            } else {
                System.out.println("Please enter only a single character.");
            }
        }

        StringBuilder combine = new StringBuilder();
        for (char character : characters) {
            combine.append(character);
        }

        System.out.println("\nCombined string: " + combine);
        scanner.close();
    }
}

