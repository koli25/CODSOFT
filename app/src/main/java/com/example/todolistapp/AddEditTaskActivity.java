package com.example.todolistapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditTaskActivity extends AppCompatActivity {

    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;
    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private EditText taskDueDateEditText;
    private Spinner taskPrioritySpinner;
    private Button saveTaskButton;
    private int taskPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        taskTitleEditText = findViewById(R.id.task_title);
        taskDescriptionEditText = findViewById(R.id.task_description);
        taskDueDateEditText = findViewById(R.id.task_due_date);
        taskPrioritySpinner = findViewById(R.id.task_priority);
        saveTaskButton = findViewById(R.id.save_task_button);

        Intent intent = getIntent();
        taskPosition = intent.getIntExtra("TASK_POSITION", -1);

        if (taskPosition != -1) {
            // Edit existing task
            // Load task details from database
            loadTaskDetails(taskPosition);
            saveTaskButton.setOnClickListener(v -> updateTask());
        } else {
            // Add new task
            saveTaskButton.setOnClickListener(v -> addTask());
        }
    }

    private void loadTaskDetails(int position) {
        // Logic to load task details from the database
    }

    private void addTask() {
        String title = taskTitleEditText.getText().toString();
        String description = taskDescriptionEditText.getText().toString();
        String dueDate = taskDueDateEditText.getText().toString();
        String priority = taskPrioritySpinner.getSelectedItem().toString();

        database.execSQL("INSERT INTO tasks (title, description, due_date, priority, completed) VALUES (?, ?, ?, ?, ?)",
                new Object[]{title, description, dueDate, priority, 0});
        finish();
    }

    private void updateTask() {
        String title = taskTitleEditText.getText().toString();
        String description = taskDescriptionEditText.getText().toString();
        String dueDate = taskDueDateEditText.getText().toString();
        String priority = taskPrioritySpinner.getSelectedItem().toString();

        // Update existing task in database
        // Finish the activity
    }
}
