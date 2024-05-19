package com.task.executioner.impl;

import com.task.actions.impl.TaskActionsImpl;
import com.task.executioner.TaskExecutioner;

public class TaskExecutionerImpl implements TaskExecutioner {
    TaskActionsImpl taskActions = new TaskActionsImpl();

    @Override
    public void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                taskActions.show();
                break;
            case "add":
                taskActions.add(commandRest[1]);
                break;
            case "check":
                taskActions.check(commandRest[1]);
                break;
            case "uncheck":
                taskActions.uncheck(commandRest[1]);
                break;
            case "deadline":
                String[] idDate = commandRest[1].split(" ", 2);
                taskActions.setDeadline(idDate[0], idDate[1]);
                break;
            case "today":
                taskActions.showToday();
                break;
            case "delete":
                taskActions.delete(commandRest[1]);
                break;
            case "view":
                String viewCommand = commandRest[1];
                switch (viewCommand) {
                    case "by date":
                        taskActions.viewByDate();
                        break;
                    case "by deadline":
                        taskActions.viewByDeadline();
                        break;
                    case "by project":
                        taskActions.viewByProject();
                        break;
                    default:
                        taskActions.error(commandLine);
                        break;
                }
                break;
            case "help":
                taskActions.help();
                break;
            default:
                taskActions.error(commandLine);
                break;
        }
    }
}
