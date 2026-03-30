package cli;

import core.FileOperations;

import java.util.Scanner;

public class CLI {

    public static void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("   MiniOS CLI (type 'help')");
        System.out.println("==================================");

        while (true) {
            System.out.print("MiniOS > ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) continue;

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting MiniOS...");
                break;
            }

            String[] parts = input.split(" ");

            try {
                switch (parts[0].toLowerCase()) {

                    case "list":
                        System.out.println(FileOperations.listFiles());
                        break;

                    case "create":
                        if (parts.length < 2) {
                            System.out.println("Usage: create <filename>");
                        } else {
                            System.out.println(FileOperations.createFile(parts[1]));
                        }
                        break;

                    case "rename":
                        if (parts.length < 3) {
                            System.out.println("Usage: rename <old> <new>");
                        } else {
                            System.out.println(FileOperations.renameFile(parts[1], parts[2]));
                        }
                        break;

                    case "move":
                        if (parts.length < 3) {
                            System.out.println("Usage: move <src> <dest>");
                        } else {
                            System.out.println(FileOperations.moveFile(parts[1], parts[2]));
                        }
                        break;

                    case "delete":
                        if (parts.length < 2) {
                            System.out.println("Usage: delete <file>");
                        } else {
                            System.out.println(FileOperations.deleteFile(parts[1]));
                        }
                        break;

                    case "help":
                        printHelp();
                        break;

                    default:
                        System.out.println("Invalid command. Type 'help'");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printHelp() {
        System.out.println("""
Commands:
list
create <file>
rename <old> <new>
move <src> <dest>
delete <file>
help
exit
""");
    }
}