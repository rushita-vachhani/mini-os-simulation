package gui;

import core.FileOperations;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    private static List<String> history = new ArrayList<>();

    private static final Color BG_DARK     = new Color(30, 30, 30);
    private static final Color BG_PANEL    = new Color(45, 45, 45);
    private static final Color BG_INPUT    = new Color(55, 55, 55);
    private static final Color TEXT_WHITE  = new Color(220, 220, 220);
    private static final Color TEXT_GREEN  = new Color(80, 200, 120);
    private static final Color TEXT_YELLOW = new Color(255, 200, 60);
    private static final Color ACCENT_BLUE = new Color(60, 130, 220);

    public static void run() {

        JFrame frame = new JFrame("MiniOS - File Manager");
        frame.setSize(750, 580);
        frame.setMinimumSize(new Dimension(600, 420));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG_DARK);

        String currentDir = FileOperations.getCurrentDirectory();

        JLabel title = new JLabel("  MiniOS File Manager", JLabel.LEFT);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(TEXT_GREEN);

        JLabel dirLabel = new JLabel("Dir: " + currentDir + "  ", JLabel.RIGHT);
        dirLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
        dirLabel.setForeground(TEXT_YELLOW);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BG_PANEL);
        titlePanel.setBorder(new EmptyBorder(8, 10, 8, 10));
        titlePanel.add(title, BorderLayout.WEST);
        titlePanel.add(dirLabel, BorderLayout.EAST);

        JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setBackground(BG_DARK);
        output.setForeground(TEXT_WHITE);
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));
        output.setCaretColor(TEXT_WHITE);
        output.setBorder(new EmptyBorder(8, 10, 8, 10));
        output.setText("Welcome to MiniOS!\nDirectory: " + currentDir + "\n\n");

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createLineBorder(BG_PANEL, 2));
        scroll.getViewport().setBackground(BG_DARK);

        JLabel inputLabel = new JLabel("Input:");
        inputLabel.setForeground(TEXT_YELLOW);
        inputLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        JTextField input = new JTextField();
        input.setBackground(BG_INPUT);
        input.setForeground(TEXT_WHITE);
        input.setCaretColor(TEXT_WHITE);
        input.setFont(new Font("Monospaced", Font.PLAIN, 13));
        input.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_BLUE, 1),
            new EmptyBorder(4, 8, 4, 8)
        ));

        JPanel inputRow = new JPanel(new BorderLayout(8, 0));
        inputRow.setBackground(BG_PANEL);
        inputRow.setBorder(new EmptyBorder(8, 10, 8, 10));
        inputRow.add(inputLabel, BorderLayout.WEST);
        inputRow.add(input, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 3, 8, 8));
        buttonPanel.setBackground(BG_DARK);
        buttonPanel.setBorder(new EmptyBorder(8, 10, 12, 10));

        JButton listBtn       = makeButton("List",        new Color(60, 130, 220));
        JButton createBtn     = makeButton("Create",      new Color(60, 180, 100));
        JButton mkdirBtn      = makeButton("Mkdir",       new Color(40, 150, 80));
        JButton renameBtn     = makeButton("Rename",      new Color(180, 130, 60));
        JButton moveBtn       = makeButton("Move",        new Color(130, 60, 180));
        JButton copyBtn       = makeButton("Copy",        new Color(100, 60, 200));
        JButton deleteBtn     = makeButton("Delete",      new Color(200, 60, 60));
        JButton searchBtn     = makeButton("Search",      new Color(60, 180, 180));
        JButton logsBtn       = makeButton("View Logs",   new Color(90, 90, 160));
        JButton listTrashBtn  = makeButton("List Trash",  new Color(140, 80, 40));
        JButton restoreBtn    = makeButton("Restore",     new Color(160, 120, 40));
        JButton emptyTrashBtn = makeButton("Empty Trash", new Color(160, 40, 40));
        JButton historyBtn    = makeButton("History",     new Color(100, 100, 100));
        JButton clearBtn      = makeButton("Clear",       new Color(70, 70, 70));

        buttonPanel.add(listBtn);
        buttonPanel.add(createBtn);
        buttonPanel.add(mkdirBtn);
        buttonPanel.add(renameBtn);
        buttonPanel.add(moveBtn);
        buttonPanel.add(copyBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(logsBtn);
        buttonPanel.add(listTrashBtn);
        buttonPanel.add(restoreBtn);
        buttonPanel.add(emptyTrashBtn);
        buttonPanel.add(historyBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(new JLabel());

        JLabel statusBar = new JLabel("  Ready");
        statusBar.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusBar.setForeground(new Color(140, 140, 140));
        statusBar.setBackground(BG_PANEL);
        statusBar.setOpaque(true);
        statusBar.setBorder(new EmptyBorder(4, 10, 4, 10));

        listBtn.addActionListener(e    -> execute("list",   input, output, statusBar, frame));
        createBtn.addActionListener(e  -> execute("create", input, output, statusBar, frame));
        mkdirBtn.addActionListener(e   -> execute("mkdir",  input, output, statusBar, frame));
        renameBtn.addActionListener(e  -> execute("rename", input, output, statusBar, frame));
        moveBtn.addActionListener(e    -> execute("move",   input, output, statusBar, frame));
        copyBtn.addActionListener(e    -> execute("copy",   input, output, statusBar, frame));
        deleteBtn.addActionListener(e  -> confirmDelete(input, output, statusBar, frame));
        searchBtn.addActionListener(e  -> execute("search", input, output, statusBar, frame));
        logsBtn.addActionListener(e       -> showLogs(output, statusBar));
        listTrashBtn.addActionListener(e  -> { output.append(FileOperations.listTrash() + "\n"); scrollToBottom(output); statusBar.setText("  Showing trash"); });
        restoreBtn.addActionListener(e    -> execute("restore",    input, output, statusBar, frame));
        emptyTrashBtn.addActionListener(e -> confirmEmptyTrash(output, statusBar, frame));
        historyBtn.addActionListener(e    -> showHistory(output, statusBar));
        clearBtn.addActionListener(e   -> { output.setText(""); statusBar.setText("  Output cleared"); });

        input.addActionListener(e -> execute(input.getText(), input, output, statusBar, frame));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BG_DARK);
        bottomPanel.add(inputRow, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusBar, BorderLayout.SOUTH);

        frame.setLayout(new BorderLayout());
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JButton makeButton(String label, Color color) {
        JButton btn = new JButton(label);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 12, 8, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private static void confirmDelete(JTextField input, JTextArea output, JLabel statusBar, JFrame frame) {
        String args = input.getText().trim();
        if (args.isEmpty()) {
            output.append("> delete\nUsage: enter filename in input, then click Delete\n\n");
            scrollToBottom(output);
            return;
        }

        String password = "";
        if (FileOperations.isProtected(args)) {
            JPasswordField passwordField = new JPasswordField();
            int result = JOptionPane.showConfirmDialog(
                frame,
                passwordField,
                "\"" + args + "\" is protected. Enter admin password:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (result != JOptionPane.OK_OPTION) {
                output.append("> delete " + args + "\nDelete cancelled.\n\n");
                scrollToBottom(output);
                statusBar.setText("  Delete cancelled");
                input.setText("");
                return;
            }
            password = new String(passwordField.getPassword());
        } else {
            int choice = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to delete \"" + args + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (choice != JOptionPane.YES_OPTION) {
                output.append("> delete " + args + "\nDelete cancelled.\n\n");
                scrollToBottom(output);
                statusBar.setText("  Delete cancelled");
                input.setText("");
                return;
            }
        }

        String resultMsg = FileOperations.deleteFile(args, password);
        output.append("> delete " + args + "\n" + resultMsg + "\n\n");
        scrollToBottom(output);
        statusBar.setText("  Last: delete");
        input.setText("");
    }

    private static void confirmEmptyTrash(JTextArea output, JLabel statusBar, JFrame frame) {
        int choice = JOptionPane.showConfirmDialog(
            frame,
            "Permanently delete everything in trash? This cannot be undone.",
            "Empty Trash",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        if (choice == JOptionPane.YES_OPTION) {
            output.append(FileOperations.emptyTrash() + "\n\n");
            scrollToBottom(output);
            statusBar.setText("  Trash emptied");
        }
    }

    private static void showLogs(JTextArea output, JLabel statusBar) {
        output.append("--- Log File ---\n" + FileOperations.readLogs() + "\n");
        scrollToBottom(output);
        statusBar.setText("  Showing logs");
    }

    private static void execute(String command, JTextField input, JTextArea output, JLabel statusBar, JFrame frame) {
        String args = input.getText().trim();

        String fullCommand;
        if (!command.contains(" ") && !command.equals("list")) {
            fullCommand = command + (args.isEmpty() ? "" : " " + args);
        } else {
            fullCommand = command;
        }

        if (fullCommand.trim().isEmpty()) return;

        history.add(fullCommand);

        String[] parts = fullCommand.trim().split("\\s+");
        String result;

        try {
            switch (parts[0].toLowerCase()) {
                case "list":
                    result = FileOperations.listFiles();
                    break;
                case "create":
                    if (parts.length < 2) { result = "Usage: enter filename in input, then click Create"; break; }
                    result = FileOperations.createFile(parts[1]);
                    break;
                case "mkdir":
                    if (parts.length < 2) { result = "Usage: enter foldername in input, then click Mkdir"; break; }
                    result = FileOperations.makeDirectory(parts[1]);
                    break;
                case "rename":
                    if (parts.length < 3) { result = "Usage: enter <oldname> <newname> in input, then click Rename"; break; }
                    result = FileOperations.renameFile(parts[1], parts[2]);
                    break;
                case "move":
                    if (parts.length < 3) { result = "Usage: enter <src> <dest> in input, then click Move"; break; }
                    result = FileOperations.moveFile(parts[1], parts[2]);
                    break;
                case "copy":
                    if (parts.length < 3) { result = "Usage: enter <src> <dest> in input, then click Copy"; break; }
                    result = FileOperations.copyFile(parts[1], parts[2]);
                    break;
                case "delete":
                    if (parts.length < 2) { result = "Usage: enter filename in input, then click Delete"; break; }
                    result = FileOperations.deleteFile(parts[1], "");
                    break;
                case "search":
                    if (parts.length < 2) { result = "Usage: enter keyword in input, then click Search"; break; }
                    result = FileOperations.searchFile(parts[1]);
                    break;
                case "restore":
                    if (parts.length < 2) { result = "Usage: enter filename in input, then click Restore"; break; }
                    result = FileOperations.restoreFile(parts[1]);
                    break;
                default:
                    result = "Unknown command: " + parts[0];
            }
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }

        output.append("> " + fullCommand + "\n" + result + "\n\n");
        scrollToBottom(output);
        statusBar.setText("  Last: " + parts[0]);
        input.setText("");
    }

    private static void showHistory(JTextArea output, JLabel statusBar) {
        if (history.isEmpty()) {
            output.append("No commands run yet.\n\n");
        } else {
            output.append("--- Command History ---\n");
            for (int i = 0; i < history.size(); i++) {
                output.append((i + 1) + ". " + history.get(i) + "\n");
            }
            output.append("\n");
        }
        scrollToBottom(output);
        statusBar.setText("  Showing history");
    }

    private static void scrollToBottom(JTextArea output) {
        output.setCaretPosition(output.getDocument().getLength());
    }
}
