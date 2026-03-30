import cli.CLI;
import gui.GUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter mode (cli/gui/exit): ");
            String choice = sc.nextLine().trim();

            if (choice.equalsIgnoreCase("cli")) {
                CLI.run();
            } else if (choice.equalsIgnoreCase("gui")) {
                GUI.run();
            } else if (choice.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Type cli, gui, or exit.");
            }
        }
    }
}