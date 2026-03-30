package gui;

import core.FileOperations;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    private static List<String> history = new ArrayList<>();

    public static void run() {

        JFrame frame = new JFrame("MiniOS - GUI");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Output area
        JTextArea output = new JTextArea();
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);

        // Input field
        JTextField input = new JTextField();

        // Panel for buttons
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));

        JButton listBtn = new JButton("List");
        JButton createBtn = new JButton("Create");
        JButton renameBtn = new JButton("Rename");
        JButton moveBtn = new JButton("Move");
        JButton deleteBtn = new JButton("Delete");
        JButton searchBtn = new JButton("Search");
        JButton historyBtn = new JButton("History");
        JButton clearBtn = new JButton("Clear");

        // Add buttons
        panel.add(listBtn);
        panel.add(createBtn);
        panel.add(renameBtn);
        panel.add(moveBtn);
        panel.add(deleteBtn);
        panel.add(searchBtn);
        panel.add(historyBtn);
        panel.add(clearBtn);

        // Button actions
        listBtn.addActionListener(e -> execute("list", output));
        createBtn.addActionListener(e -> execute("create " + input.getText(), output));
        renameBtn.addActionListener(e -> execute("rename " + input.getText(), output));
        moveBtn.addActionListener(e -> execute("move " + input.getText(), output));
        deleteBtn.addActionListener(e -> execute("delete " + input.getText(), output));
        searchBtn.addActionListener(e -> execute("search " + input.getText(), output));
        historyBtn.addActionListener(e -> showHistory(output));
        clearBtn.addActionListener(e -> output.setText(""));

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(input, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void execute(String command, JTextArea output) {
        if (command.trim().isEmpty()) return;

        history.add(command);

        String[] parts = command.split(" ");
        String result;

        try {
            switch (parts[0]) {

                case "list":
                    result = FileOperations.listFiles();
                    break;

                case "create":
                    result = FileOperations.createFile(parts[1]);
                    break;

                case "rename":
                    result = FileOperations.renameFile(parts[1], parts[2]);
                    break;

                case "move":
                    result = FileOperations.moveFile(parts[1], parts[2]);
                    break;

                case "delete":
                    result = FileOperations.deleteFile(parts[1]);
                    break;

                case "search":
                    result = FileOperations.searchFile(parts[1]);
                    break;

                default:
                    result = "Invalid command";
            }

        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }

        output.append("> " + command + "\n" + result + "\n\n");
    }

    private static void showHistory(JTextArea output) {
        output.append("Command History:\n");
        for (String cmd : history) {
            output.append(cmd + "\n");
        }
        output.append("\n");
    }
}