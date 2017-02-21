package com.example.momintariq.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.momintariq.habittracker.data.HabitContract.HabitEntry;
import com.example.momintariq.habittracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Insert data when the button is clicked
        Button button = (Button)findViewById(R.id.insert_dummy_data);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                displayDatabaseInfo();
            }
        });

        dbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    // Insert dummy data
    private void insertData() {
        // Create a SQLiteDatabase
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Store values in ContentValues
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "Play videogames");
        values.put(HabitEntry.COLUMN_AGE_WHEN_STARTED, 6);

        // Insert the content values into a database
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    // Display database contents
    private void displayDatabaseInfo() {
        // Create SQLiteDatabase
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Create a cursor (2-dimensional representation of database)
        Cursor cursor = db.query(HabitEntry.TABLE_NAME, null, null, null, null, null, null);

        try {
            // Build the text view with database info
            TextView textView = (TextView)findViewById(R.id.display_database);
            textView.setText("Number of rows in the table: " + cursor.getCount() + "\n");
            textView.append(HabitEntry.COLUMN_HABIT_NAME + " - " + HabitEntry.COLUMN_AGE_WHEN_STARTED + "\n");

            while(cursor.moveToNext()) {
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                textView.append(name + " - " + age + "\n");
            }
        } finally {
            cursor.close();
        }
    }
}
