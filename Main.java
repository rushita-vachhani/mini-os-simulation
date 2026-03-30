import core.FileOperations;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Testing MiniOS Core Functions ===");

        // Test list
        System.out.println(FileOperations.listFiles());

        // Test create
        System.out.println(FileOperations.createFile("test1.txt"));

        // Test rename
        System.out.println(FileOperations.renameFile("test1.txt", "test2.txt"));

        // Create folder manually before running move
        System.out.println(FileOperations.moveFile("test2.txt", "folder/test2.txt"));

    }
}