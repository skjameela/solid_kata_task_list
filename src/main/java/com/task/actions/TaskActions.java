package com.task.actions;

public interface TaskActions {
    void show();
    void add(String commandLine);
    void addProject(String name);
    void check(String idString);
    void uncheck(String idString);
    void setDone(String idString, boolean done);
    void help();
    void error(String command);
    void setDeadline(String idString, String deadline);
    void showToday();
    void delete(String idString);
    void viewByDate();
    void viewByDeadline();
    void viewByProject();
}
