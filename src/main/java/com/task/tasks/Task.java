package com.codurance.training.tasks;

import java.time.LocalDate;

public final class Task {
    private final String id;
    private final String description;
    private LocalDate deadline;  // Make this non-final to allow modification
    private boolean done;

    public Task(String id, String description, LocalDate deadline, boolean done) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
