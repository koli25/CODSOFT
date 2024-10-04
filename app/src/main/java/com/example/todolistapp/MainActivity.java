package com.example.todolistapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;
    private ListView taskListView;
    private Button addTaskButton;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        taskListView = findViewById(R.id.task_list_view);
        addTaskButton = findViewById(R.id.add_task_button);
        taskList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);

        loadTasks();

        addTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
            startActivity(intent);
        });

        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
            intent.putExtra("TASK_POSITION", position);
            startActivity(intent);
        });

        taskListView.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteTask(position);
            return true;
        });
    }

    private void loadTasks() {
        taskList.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM tasks", null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            boolean completed = cursor.getInt(cursor.getColumnIndex("completed")) > 0;
            taskList.add((completed ? "[Completed] " : "[Active] ") + title);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void deleteTask(int position) {
        String title = taskList.get(position).replace("[Completed] ", "").replace("[Active] ", "");
        database.execSQL("DELETE FROM tasks WHERE title = ?", new Object[]{title});
        loadTasks();
    }
}
