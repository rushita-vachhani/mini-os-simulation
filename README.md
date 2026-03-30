# MiniOS - File Management Simulation

This is our OS course project — a mini file management system built in Java. It supports both a CLI and a GUI (Swing), and simulates basic OS-level file operations like create, move, copy, delete, and search.

---

## Project Structure

```
mini-os-simulation/
├── Main.java               starts the program, asks cli or gui
├── cli/
│   └── CLI.java            handles command input and routing
├── core/
│   └── FileOperations.java all the actual file operation logic
├── gui/
│   └── GUI.java            swing window with buttons
├── utils/
│   ├── Logger.java         writes logs to logs/log.txt
│   └── AdminAuth.java      manages protected folders and admin password
└── logs/
    └── log.txt             auto created when you run the program
```

---

## How to Run (Eclipse)

1. Open Eclipse → File > Open Projects from File System → select the `mini-os-simulation` folder
2. Right-click `Main.java` → Run As > Java Application
3. Type `cli` or `gui` in the console and hit Enter

---

## CLI Commands

| Command | Example | What it does |
|---|---|---|
| list | `list` | shows all files with size and date |
| create | `create hello.txt` | creates a new file |
| mkdir | `mkdir docs` | creates a new folder |
| rename | `rename old.txt new.txt` | renames a file |
| move | `move hello.txt docs/hello.txt` | moves file to another location |
| copy | `copy hello.txt docs/hello.txt` | copies file, original stays |
| delete | `delete hello.txt` | moves file to trash |
| listtrash | `listtrash` | shows files currently in trash |
| restore | `restore hello.txt` | restores file from trash |
| emptytrash | `emptytrash` | permanently deletes everything in trash |
| search | `search hello` | searches all folders for matching name |
| logs | `logs` | prints the log file |
| switch | `switch` | opens GUI without closing CLI |
| help | `help` | lists all commands |
| exit | `exit` | exits CLI, goes back to mode selection |

---

## GUI

The GUI has buttons for every operation. Just type the filename in the input field and click the button. A few extra things:

- Clicking Delete shows a confirmation popup before actually deleting
- View Logs button shows log.txt right in the output area
- History button shows everything you ran in that session
- Current folder is shown in the top right corner
- Press Enter in the input field instead of clicking if you prefer

---

## Recycle Bin

Deleted files are not permanently removed — they go to a `trash/` folder first, just like a real OS recycle bin.

**CLI commands:**
```
delete hello.txt        moves hello.txt to trash/
listtrash               shows everything currently in trash
restore hello.txt       brings hello.txt back from trash
emptytrash              permanently deletes everything in trash
```

**GUI buttons:** List Trash, Restore, Empty Trash

- Empty Trash asks for confirmation before permanently deleting
- Files in trash can always be recovered using restore until trash is emptied

---

## Admin Access Control

Some folders are protected and cannot be deleted without an admin password. This simulates how real operating systems restrict access to system directories.

Protected folders: `cli`, `gui`, `core`, `utils`, `logs`

**In CLI:**
```
MiniOS > delete cli
"cli" is protected. Enter admin password:
```
- Wrong password → Access denied
- Correct password → Deleted

**In GUI:**
- Deleting a normal file → shows a "Are you sure?" confirmation
- Deleting a protected folder → skips confirmation, asks for admin password directly

Admin password: `admin123`

Unauthorized attempts are logged as WARN in `logs/log.txt`.

---

## Logging

Every operation gets logged to `logs/log.txt` automatically with a timestamp and level:

```
2026-03-30T11:15:48 [INFO] Created file: hello.txt
2026-03-30T11:16:02 [WARN] File already exists: hello.txt
2026-03-30T11:16:20 [ERROR] Rename failed: file not found
```

---

## How to Compile (Terminal)

```
javac Main.java core/*.java cli/*.java gui/*.java utils/*.java
java Main
```
