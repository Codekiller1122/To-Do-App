// Task.java

package com.android.tools.android_assignment_3;

public class Task {
    private long id;  // Assuming you have an ID field in your Task class
    private String title;
    private String description;
    private String dueDate;


    // Constructor with arguments
    public Task(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }
    public Task() {
        // Provide default values or leave fields uninitialized
    }

    // Getters and setters for other fields, if needed
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
