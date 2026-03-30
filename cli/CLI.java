package cli;

import core.FileOperations;
import gui.GUI;

import java.util.Scanner;

public class CLI {

    public static void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("   MiniOS CLI (type 'help')");
        System.out.println("==================================");
        System.out.println("Directory: " + FileOperations.getCurrentDirectory());

        while (true) {
            System.out.print("MiniOS > ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) continue;

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting MiniOS...");
                break;
            }

            String[] parts = input.split("\\s+");

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
                            String password = "";
                            if (FileOperations.isProtected(parts[1])) {
                                System.out.print("\"" + parts[1] + "\" is protected. Enter admin password: ");
                                password = sc.nextLine().trim();
                            }
                            System.out.println(FileOperations.deleteFile(parts[1], password));
                        }
                        break;

                    case "search":
                        if (parts.length < 2) {
                            System.out.println("Usage: search <keyword>");
                        } else {
                            System.out.println(FileOperations.searchFile(parts[1]));
                        }
                        break;

                    case "mkdir":
                        if (parts.length < 2) {
                            System.out.println("Usage: mkdir <foldername>");
                        } else {
                            System.out.println(FileOperations.makeDirectory(parts[1]));
                        }
                        break;

                    case "copy":
                        if (parts.length < 3) {
                            System.out.println("Usage: copy <src> <dest>");
                        } else {
                            System.out.println(FileOperations.copyFile(parts[1], parts[2]));
                        }
                        break;

                    case "listtrash":
                        System.out.println(FileOperations.listTrash());
                        break;

                    case "restore":
                        if (parts.length < 2) {
                            System.out.println("Usage: restore <filename>");
                        } else {
                            System.out.println(FileOperations.restoreFile(parts[1]));
                        }
                        break;

                    case "emptytrash":
                        System.out.println(FileOperations.emptyTrash());
                        break;

                    case "logs":
                        System.out.println(FileOperations.readLogs());
                        break;

                    case "switch":
                        System.out.println("Launching GUI...");
                        GUI.run();
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
mkdir <foldername>
rename <old> <new>
move <src> <dest>
copy <src> <dest>
delete <file>        (moves to trash)
listtrash            (show files in trash)
restore <file>       (restore from trash)
emptytrash           (permanently delete trash)
search <keyword>     (searches all subfolders too)
logs
switch               (open GUI without exiting CLI)
help
exit
""");
    }
}