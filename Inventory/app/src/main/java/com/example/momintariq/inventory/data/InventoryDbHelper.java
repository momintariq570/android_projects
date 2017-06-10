package com.example.momintariq.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.momintariq.inventory.data.InventoryContract.InventoryEntry;

/**
 * Created by momintariq on 3/16/17.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "inventory.db";

    private static final String SQL_CREATE_INVENTORY_TABLE =
            "CREATE TABLE " + InventoryEntry.TABLE_NAME + " (" +
                    InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                    InventoryEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL DEFAULT 0, " +
                    InventoryEntry.COLUMN_PRODUCT_SUPPLIER + " INTEGER NOT NULL, " +
                    InventoryEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0, " +
                    InventoryEntry.COLUMN_PRODUCT_PICTURE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + InventoryEntry.TABLE_NAME;

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }
}
