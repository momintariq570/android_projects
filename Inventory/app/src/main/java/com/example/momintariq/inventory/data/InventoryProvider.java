package com.example.momintariq.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static com.example.momintariq.inventory.data.InventoryContract.InventoryEntry;

/**
 * Created by momintariq on 3/17/17.
 */

public class InventoryProvider extends ContentProvider {

    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();
    private InventoryDbHelper dbHelper;
    private static final int INVENTORY = 100;
    private static final int INVENTORY_ID = 101;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_INVENTORY, INVENTORY);
        uriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_INVENTORY + "/#", INVENTORY_ID);
    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        // Initialize the InventoryDbHelper object to gain access to the pets database
        dbHelper = new InventoryDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);

        switch (match) {
            case INVENTORY:
                cursor = db.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case INVENTORY_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return InventoryEntry.CONTENT_LIST_TYPE;
            case INVENTORY_ID:
                return InventoryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int deleteCount = 0;

        switch (match) {
            case INVENTORY:
                // Delete all rows that match the selection and selectionArgs
                deleteCount = db.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case INVENTORY_ID:
                // Delete a single row given by the ID in the URI
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                deleteCount = db.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if(deleteCount != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case INVENTORY_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private Uri insertProduct(Uri uri, ContentValues values) {

        // Data validation
        String productName = values.getAsString(InventoryEntry.COLUMN_PRODUCT_NAME);
        if(productName == null || productName.length() == 0) {
            throw new IllegalArgumentException("Product requires a valid name");
        }

        String productPriceString = values.getAsString(InventoryEntry.COLUMN_PRODUCT_PRICE);
        if(productPriceString == null || productPriceString.length() == 0) {
            throw new IllegalArgumentException("Product requires a valid price");
        }

        String productSupplier = values.getAsString(InventoryEntry.COLUMN_PRODUCT_SUPPLIER);
        if(productSupplier == null || productSupplier.length() == 0) {
            throw new IllegalArgumentException("Product requires a valid supplier");
        }

        String productQuantityString = values.getAsString(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
        if(productQuantityString == null || productQuantityString.length() == 0) {
            throw new IllegalArgumentException("Product requires a valid quantity");
        }

        String productImageUriString = values.getAsString(InventoryEntry.COLUMN_PRODUCT_PICTURE);
        if(productImageUriString == null || productImageUriString.length() == 0) {
            throw new IllegalArgumentException("Product requires a valid image");
        }

        // Insert a new pet into the pets database table with the given ContentValues
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(InventoryEntry.TABLE_NAME, null, values);

        // Check if the row was inserted properly
        if(id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Listens for change in the database given a uri
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the uri with the id appended at the end for the new row added to the database
        return ContentUris.withAppendedId(uri, id);
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if(values.size() == 0) {
            return 0;
        }

        // Data validation
        if(values.containsKey(InventoryEntry.COLUMN_PRODUCT_NAME)) {
            String productName = values.getAsString(InventoryEntry.COLUMN_PRODUCT_NAME);
            if(productName == null || productName.length() == 0) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }

        if(values.containsKey(InventoryEntry.COLUMN_PRODUCT_PRICE)) {
            String productPriceString = values.getAsString(InventoryEntry.COLUMN_PRODUCT_PRICE);
            if(productPriceString == null || productPriceString.length() == 0) {
                throw new IllegalArgumentException("Product requires a valid price");
            }
        }

        if(values.containsKey(InventoryEntry.COLUMN_PRODUCT_QUANTITY)) {
            Integer productQuantity = values.getAsInteger(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            if(productQuantity == null || productQuantity < 0) {
                throw new IllegalArgumentException("Product requires a valid quantity");
            }
        }

        if(values.containsKey(InventoryEntry.COLUMN_PRODUCT_SUPPLIER)) {
            String productSupplier = values.getAsString(InventoryEntry.COLUMN_PRODUCT_SUPPLIER);
            if(productSupplier == null || productSupplier.length() == 0) {
                throw new IllegalArgumentException("Product requires a valid supplier");
            }
        }

        if(values.containsKey(InventoryEntry.COLUMN_PRODUCT_PICTURE)) {
            String productImageUriString = values.getAsString(InventoryEntry.COLUMN_PRODUCT_PICTURE);
            if(productImageUriString == null || productImageUriString.length() == 0) {
                throw new IllegalArgumentException("Product requires a valid image");
            }
        }

        // Update the selected product(s) in the inventory table with the given ContentValues
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated = db.update(InventoryEntry.TABLE_NAME, values, selection, selectionArgs);

        if(rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
