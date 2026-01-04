package com.postgres.studentManagementSystem.enums;

public enum CourseStates {
    TO_DO(1,"To Do"),
    IN_PROGRESS(2,"in progress"),
    COMPLETED(3, "completed");

    private final int value;
    private final String description;

    CourseStates(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
