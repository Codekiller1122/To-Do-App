package com.android.tools.android_assignment_3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private TaskDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private TaskListAdapter adapter; // Change the type to TaskListAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        dbHelper = new TaskDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new TaskListAdapter(); // Initialize the adapter with TaskListAdapter

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Load tasks from the database
        loadTasks();
    }

    private void loadTasks() {
        List<Task> taskList = dbHelper.getAllTasks();
        adapter.setTasks(taskList);
    }
}
