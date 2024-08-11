package com.android.tools.android_assignment_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    // Renamed to avoid duplication
    private static final String TASKS_TABLE = "tasks";

    // Renamed to avoid duplication
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DUE_DATE = "due_date";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TASKS_TABLE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TITLE
            + " text not null, " + COLUMN_DESCRIPTION
            + " text not null, " + COLUMN_DUE_DATE + " text not null);";

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE);
        onCreate(db);
    }

    public long addOrUpdateTask(String title, String description, String dueDate, long taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DUE_DATE, dueDate);

        if (taskId == -1) {
            return db.insert(TASKS_TABLE, null, values);
        } else {
            return db.update(TASKS_TABLE, values, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(taskId)});
        }
    }

    public void deleteTask(long taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASKS_TABLE, COLUMN_ID + " = ?",
                new String[]{String.valueOf(taskId)});
        db.close();
    }

    public Task getTaskByDescription(String description) {
        SQLiteDatabase db = this.getReadableDatabase();
        Task task = null;

        Cursor cursor = db.query(
                TASKS_TABLE,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_DUE_DATE},
                COLUMN_DESCRIPTION + " = ?",
                new String[]{description},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            task = new Task();
            task.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            task.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            task.setDueDate(cursor.getString(cursor.getColumnIndex(COLUMN_DUE_DATE)));

            cursor.close();
        }

        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TASKS_TABLE, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                task.setDueDate(cursor.getString(cursor.getColumnIndex(COLUMN_DUE_DATE)));

                taskList.add(task);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return taskList;
    }
}
