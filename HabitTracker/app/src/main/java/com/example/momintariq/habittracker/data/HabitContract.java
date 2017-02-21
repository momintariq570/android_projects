package com.example.momintariq.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by momintariq on 2/20/17.
 */

public final class HabitContract {

    private HabitContract() {}

    public static class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_AGE_WHEN_STARTED = "age_when_started";
    }
}
