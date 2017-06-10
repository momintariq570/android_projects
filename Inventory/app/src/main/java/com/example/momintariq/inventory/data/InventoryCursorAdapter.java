package com.example.momintariq.inventory.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.momintariq.inventory.R;
import com.example.momintariq.inventory.data.InventoryContract.InventoryEntry;

/**
 * Created by momintariq on 3/18/17.
 */

public class InventoryCursorAdapter extends CursorAdapter {



    public InventoryCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        // Find the view fields
        TextView productNameTextView = (TextView)view.findViewById(R.id.product_name_value);
        TextView productPriceTextView= (TextView)view.findViewById(R.id.product_price_value);
        final TextView productQuantityTextView = (TextView)view.findViewById(R.id.product_quantity_value);
        Button sellButton = (Button)view.findViewById(R.id.sell_product);

        // Extract the data from the cursor
        int productNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int productPriceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
        int productQuantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);

        String nameCursor = cursor.getString(productNameColumnIndex);
        int priceCursor = cursor.getInt(productPriceColumnIndex);
        int quantityCursor = cursor.getInt(productQuantityColumnIndex);

        final int position = cursor.getPosition();

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToPosition(position);
                Log.i("Adapter", String.valueOf(position));
                long inventoryId = cursor.getLong(cursor.getColumnIndex(InventoryEntry._ID));
                Uri uri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, inventoryId);
                int currentQuantity = Integer.parseInt(productQuantityTextView.getText().toString());
                int productQuantity = currentQuantity - 1;
                if(productQuantity < 0) {
                    return;
                } else {
                    productQuantityTextView.setText(String.valueOf(productQuantity));
                    ContentValues values = new ContentValues();
                    values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);
                    view.getContext().getContentResolver().update(uri, values, null, null);
                }
            }
        });

        // Populate the view fields
        productNameTextView.setText(nameCursor);
        productPriceTextView.setText(String.valueOf(priceCursor));
        productQuantityTextView.setText(String.valueOf(quantityCursor));
    }
}
