package com.codurance.training.actions.impl;

import com.codurance.training.actions.TaskActions;
import com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskActionsImpl implements TaskActions {
    private final PrintWriter out = new PrintWriter(System.out);

    private static final Map<String, List<Task>> tasksMap = new LinkedHashMap<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void show() {
        for (Map.Entry<String, List<Task>> project : tasksMap.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                out.printf("    [%c] %s: %s %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription(), task.getDeadline() != null ? task.getDeadline().format(formatter) : "");
            }
            out.println();
        }
        out.flush();
    }

    @Override
    public void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 3);
            String project = projectTask[0];
            String id = projectTask[1];
            String description = projectTask.length > 2 ? projectTask[2] : "";
            addTask(project, id, description, null);
        }
    }

    @Override
    public void addProject(String name) {
        tasksMap.put(name, new ArrayList<Task>());
    }

    private void addTask(String project, String id, String description, LocalDate deadline) {
        List<Task> projectTasks = tasksMap.get(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            out.flush();
            return;
        }
        projectTasks.add(new Task(id, description, deadline, false));
    }

    @Override
    public void check(String idString) {
        setDone(idString, true);
    }

    @Override
    public void uncheck(String idString) {
        setDone(idString, false);
    }

    @Override
    public void setDone(String idString, boolean done) {
        for (Map.Entry<String, List<Task>> project : tasksMap.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId().equals(idString)) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %s.", idString);
        out.println();
        out.flush();
    }

    @Override
    public void help() {
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task ID> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println("  deadline <task ID> <date>");
        out.println("  today");
        out.println("  delete <task ID>");
        out.println("  view by date");
        out.println("  view by deadline");
        out.println("  view by project");
        out.println();
        out.flush();
    }

    @Override
    public void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
        out.flush();
    }

    @Override
    public void setDeadline(String idString, String deadline) {
        LocalDate deadlineDate = LocalDate.parse(deadline, formatter);
        for (Map.Entry<String, List<Task>> project : tasksMap.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId().equals(idString)) {
                    task.setDeadline(deadlineDate);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %s.", idString);
        out.println();
        out.flush();
    }

    @Override
    public void showToday() {
        LocalDate today = LocalDate.now();
        for (Map.Entry<String, List<Task>> project : tasksMap.entrySet()) {
            for (Task task : project.getValue()) {
                if (today.equals(task.getDeadline())) {
                    out.printf("[%c] %s: %s %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription(), task.getDeadline() != null ? task.getDeadline().format(formatter) : "");
                }
            }
        }
        out.flush();
    }

    @Override
    public void delete(String idString) {
        for (Map.Entry<String, List<Task>> project : tasksMap.entrySet()) {
            Iterator<Task> iterator = project.getValue().iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getId().equals(idString)) {
                    iterator.remove();
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %s.", idString);
        out.println();
        out.flush();
    }

    @Override
    public void viewByDate() {
        List<Task> allTasks = new ArrayList<>();
        for (List<Task> projectTasks : tasksMap.values()) {
            allTasks.addAll(projectTasks);
        }
        allTasks.sort(Comparator.comparing(Task::getDeadline, Comparator.nullsLast(Comparator.naturalOrder())));
        for (Task task : allTasks) {
            out.printf("[%c] %s: %s %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription(), task.getDeadline() != null ? task.getDeadline().format(formatter) : "");
        }
        out.flush();
    }

    @Override
    public void viewByDeadline() {
        List<Task> allTasks = new ArrayList<>();
        for (List<Task> projectTasks : tasksMap.values()) {
            allTasks.addAll(projectTasks);
        }
        allTasks.sort(Comparator.comparing(Task::getDeadline, Comparator.nullsLast(Comparator.naturalOrder())));
        for (Task task : allTasks) {
            out.printf("[%c] %s: %s %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription(), task.getDeadline() != null ? task.getDeadline().format(formatter) : "");
        }
        out.flush();
    }

    @Override
    public void viewByProject() {
        show();
    }
}
