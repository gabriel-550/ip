# Enzo User Guide

Enzo is a command-line chatbot that helps you stay organized by managing your tasks. With simple text commands, you can easily track your todos, deadlines and events.

## Quick Start

1. Ensure you have Java 17 or above installed.

2. Download the latest `enzo.jar`.

3. Open a terminal, navigate to the folder containing the file, and run:

```
java -jar enzo.jar
```

4. Type a command in the terminal and press Enter.

## Features
 - Add Todo, Deadline, Event tasks
 - Display all tasks in the list
 - Mark and unmark tasks
 - Delete tasks
 - Find tasks by keyword

## Commands
> Notes on command format:
> - Words in `UPPER_CASE` are parameters that need to be provided.
> - `INDEX` refers to the task number shown in the list output.

### Adding a Todo Task: `todo`
Adds a simple task with no date or time attached.

Format: `todo DESCRIPTION`

Example: `todo read` 

```
Got it. I've added this task:
 [T][ ] read 
Now you have 1 tasks in the list.
```

### Adding a Deadline Task: `deadline`
Adds a task that must be completed by a specific date or time.

Format: `deadline DESCRIPTION /by DATE`

Example: `deadline submit assignment /by Friday 11:59pm`

```
Got it. I've added this task:
 [D][ ] submit assignment (by: Friday 11:59pm)
Now you have 2 tasks in the list.
```
### Adding an Event Task: `event`
Adds a task that spans a time range with a start and end.

Format: `event DESCRIPTION /from START /to END`

Example: `event project meeting /from Mon 2pm /to Mon 4pm`

```
Got it. I've added this task:
 [E][ ] project meeting (from: Mon 2pm to: Mon 4pm)
Now you have 3 tasks in the list.
```

### Marking a task as done:  `mark`
Marks the specified task as completed. The status icon changes from `[ ]` to `[X]`.

Format: `mark INDEX`

Example: `mark 2`

```
Nice! I've marked this task as done:
 [D][X] submit assignment (by: Friday 11:59pm)
```

### Marking a task as not done:  `unmark`
Reverts a completed task back to incomplete.

Format: `unmark INDEX`

Example: `unmark 2`

```
OK, I've marked this task as not done yet:
 [D][ ] submit assignment (by: Friday 11:59pm)
```

### Deleting a task:  `delete`
Removes the specified task from the list.

Format: `delete INDEX`

Example: `delete 1`

```
Noted. I've removed this task:
 [T][ ] read 
Now you have 2 tasks in the list.
```

### Listing all tasks:  `list`
Displays every task currently in your list, along with its type and completion status.

Format: `list`

```
Here are the tasks in your list:
 1.[D][ ] submit assignment (by: Friday 11:59pm)
 2.[E][ ] project meeting (from: Mon 2pm to: Mon 4pm)
 3.[T][X] read a book
```

Task type icons: `[T]` Todo | `[D]` Deadline | `[E]` Event
Status icons: `[X]` done | `[ ]` not done

### Finding tasks by keyword:  `find`
Searches for tasks whose descriptions contain the given keyword.

Format: `find KEYWORD`

Example: `find book`

```
Here are the matching tasks in your list:
 1.[T][ ] read book
 2.[D][ ] return book (by: June 6th)
```

If no tasks match, Enzo will let you know:
```
No matching tasks found.
```

### Exiting the application:  `bye`
Exits Enzo. Your tasks are already saved, they will be waiting when you return.

Format: `bye`

```
Bye. I'll be right here when you need me again!
```


### Saving and Loading Data
Tasks are saved automatically to `./data/enzo.txt` after every change. No manual saving is needed. 

The file and folder are created automatically on first run.

## Command Summary

| Command    | Format                                      |
| ---------- | ------------------------------------------- |
| `todo`     | `todo DESCRIPTION`                          |
| `deadline` | `deadline DESCRIPTION /by DATE`             |
| `event`    | `event DESCRIPTION /from START /to END`     |
| `mark`     | `mark INDEX`                                |
| `unmark`   | `unmark INDEX`                              |
| `delete`   | `delete INDEX`                              |
| `list`     | `list`                                      |
| `find`     | `find KEYWORD`                              |
| `bye`      | `bye`                                       |