package com.example.momintariq.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.momintariq.habittracker.data.HabitContract.HabitEntry;

/**
 * Created by momintariq on 2/20/17.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "habits.db";

    private static final String SQL_CREATE_PETS_TABLE =
            "CREATE TABLE " + HabitEntry.TABLE_NAME + " (" +
                    HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, " +
                    HabitEntry.COLUMN_AGE_WHEN_STARTED + " INTEGER NOT NULL DEFAULT 0)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
