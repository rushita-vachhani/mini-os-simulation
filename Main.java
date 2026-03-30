import cli.CLI;
import gui.GUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter mode (cli/gui): ");
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("cli")) {
            CLI.run();
        } else {
            GUI.run();
        }
    }
}