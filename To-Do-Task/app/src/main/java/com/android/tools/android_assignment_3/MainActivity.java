package com.android.tools.android_assignment_3;

import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TaskDatabaseHelper dbHelper;

    private EditText editTitle;
    private EditText editDescription;
    private EditText editDueDate;
    private EditText editTaskId;

    private Button btnAdd;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnDatabase;
    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDatabaseHelper(this);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editDueDate = findViewById(R.id.editDueDate);
        editTaskId = findViewById(R.id.editTaskId); // Assuming you have an edit text for task id

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnAdd = findViewById(R.id.btnAdd);
        btnDatabase = findViewById(R.id.btnDatabase);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from EditText fields
                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();
                String dueDate = editDueDate.getText().toString();

                // Insert the task into the database
                long newTaskId = dbHelper.addOrUpdateTask(title, description, dueDate, -1);
                System.out.println("New Task ID: " + newTaskId);
            }
        });

        btnDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open TaskListActivity when "Database" button is clicked
                startActivity(new Intent(MainActivity.this, TaskListActivity.class));
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from EditText fields
                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();
                String dueDate = editDueDate.getText().toString();

                // Get the task ID from user input
                String idString = editTaskId.getText().toString();
                if (idString.isEmpty()) {
                    // Handle the case where ID is empty
                    System.out.println("Task ID cannot be empty.");
                    return;
                }

                // Parse the user input to a long value
                long taskIdToUpdate = Long.parseLong(idString);

                // Create a new Task object with updated values
                Task updatedTask = new Task(title, description, dueDate);

                // Update the task in the database
                long rowsUpdated = dbHelper.addOrUpdateTask(
                        updatedTask.getTitle(),
                        updatedTask.getDescription(),
                        updatedTask.getDueDate(),
                        taskIdToUpdate
                );

                System.out.println("Rows Updated: " + rowsUpdated);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the task ID from user input
                String idString = editTaskId.getText().toString();
                if (idString.isEmpty()) {
                    // Handle the case where ID is empty
                    System.out.println("Task ID cannot be empty.");
                    return;
                }

                // Parse the user input to a long value
                long taskIdToDelete = Long.parseLong(idString);

                // Delete the task from the database
                dbHelper.deleteTask(taskIdToDelete);
                System.out.println("Task deleted with ID: " + taskIdToDelete);
            }
        });
    }

    @Override
    protected void onDestroy() {
        // Close the database helper when the activity is destroyed
        dbHelper.close();
        super.onDestroy();
    }
}
