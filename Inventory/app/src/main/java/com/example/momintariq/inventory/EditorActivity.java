package com.example.momintariq.inventory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momintariq.inventory.data.InventoryContract.InventoryEntry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.example.momintariq.inventory.data.InventoryProvider.LOG_TAG;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_PRODUCT_LOADER = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri currentProductUri;
    EditText productNameEditText;
    EditText productPriceEditText;
    EditText productSupplierEditText;
    TextView productQuantityTextView;
    ImageView productImageView;
    Button sellButton;
    Button restockButton;
    Button orderButton;
    Button imageSelectorButton;
    int productQuantity;
    int incrementQuantityBy = 10;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        currentProductUri = intent.getData();

        if(currentProductUri == null) {
            this.setTitle(getString(R.string.editor_activity_title_add_product));
            invalidateOptionsMenu();
        } else {
            this.setTitle(getString(R.string.editor_activity_title_edit_product));
            getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);
        }

        productNameEditText = (EditText)findViewById(R.id.edit_text_product_name);
        productPriceEditText = (EditText)findViewById(R.id.edit_text_product_price);
        productSupplierEditText = (EditText)findViewById(R.id.edit_text_product_supplier);
        productQuantityTextView = (TextView)findViewById(R.id.editor_activity_quantity);
        orderButton = (Button)findViewById(R.id.editor_activity_order);
        imageSelectorButton = (Button)findViewById(R.id.select_image_button);
        sellButton = (Button)findViewById(R.id.editor_activity_sell);
        restockButton = (Button)findViewById(R.id.editor_activity_restock_button);
        productImageView = (ImageView)findViewById(R.id.product_image_view);
        if(currentProductUri == null) {
            sellButton.setVisibility(View.GONE);
            restockButton.setVisibility(View.GONE);
        }

        // Click listener for ordering and adding quantity
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = productSupplierEditText.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Nothing to say here");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, "Send email..."));
                }
                int currentQuantity = Integer.parseInt(productQuantityTextView.getText().toString());
                productQuantity = currentQuantity + incrementQuantityBy;
                productQuantityTextView.setText(String.valueOf(productQuantity));
            }
        });

        // Click listener for subtracting quantity
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQuantity = Integer.parseInt(productQuantityTextView.getText().toString());
                productQuantity = currentQuantity - 1;
                if(productQuantity < 0) {
                    productQuantityTextView.setText(String.valueOf(currentQuantity));
                } else {
                    productQuantityTextView.setText(String.valueOf(productQuantity));
                }
            }
        });

        restockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQuantity = Integer.parseInt(productQuantityTextView.getText().toString());
                productQuantity = currentQuantity + 1;
                productQuantityTextView.setText(String.valueOf(productQuantity));
            }
        });

        imageSelectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageSelector();
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(currentProductUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveProduct();
                finish();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveProduct() {
        String productName = productNameEditText.getText().toString().trim();
        String productPrice = productPriceEditText.getText().toString().trim();
        String productEmailAddress = productSupplierEditText.getText().toString().trim();
        productQuantity = Integer.parseInt(productQuantityTextView.getText().toString());

        if(TextUtils.isEmpty(productName) &&
                TextUtils.isEmpty(productPrice) &&
                TextUtils.isEmpty(productEmailAddress) &&
                imageUri == null) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, productName);
        values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, productPrice);
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER, productEmailAddress);
        values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);
        if(imageUri != null) {
            values.put(InventoryEntry.COLUMN_PRODUCT_PICTURE, imageUri.toString());
        } else {
            values.put(InventoryEntry.COLUMN_PRODUCT_PICTURE, "");
        }

        if(currentProductUri == null) {
            Uri uriNewProduct = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
            if(uriNewProduct == null) {
                Toast.makeText(this, getString(R.string.editor_insert_product_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_product_success), Toast.LENGTH_SHORT).show();
            }
        } else {
            int updateProductRowCount = getContentResolver().update(currentProductUri, values, null, null);
            if(updateProductRowCount <= 0) {
                Toast.makeText(this, getString(R.string.editor_update_product_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_update_product_success), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteProduct() {
        int numOfProductsDeleted = getContentResolver().delete(currentProductUri, null, null);
        if(numOfProductsDeleted == 0) {
            Toast.makeText(this, getString(R.string.editor_delete_product_failed), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.editor_delete_product_success), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PRODUCT_PRICE,
                InventoryEntry.COLUMN_PRODUCT_SUPPLIER,
                InventoryEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryEntry.COLUMN_PRODUCT_PICTURE
        };

        if(currentProductUri != null) {
            return new CursorLoader(this, currentProductUri, projection, null, null, null);
        } else {
            return new CursorLoader(this, InventoryEntry.CONTENT_URI, projection, null, null, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor.moveToFirst() && currentProductUri != null) {
            int columnProductNameIdx = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int columnProductPriceIdx = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
            int columnProductSupplierIdx = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_SUPPLIER);
            int columnProductQuantityIdx = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            int columnProductPictureIdx = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PICTURE);

            String productName = cursor.getString(columnProductNameIdx);
            Integer productPrice = cursor.getInt(columnProductPriceIdx);
            Integer productQuantity = cursor.getInt(columnProductQuantityIdx);
            String productSupplier = cursor.getString(columnProductSupplierIdx);
            String productImageUriString = cursor.getString(columnProductPictureIdx);

            imageUri = Uri.parse(productImageUriString);

            productNameEditText.setText(productName);
            productPriceEditText.setText(String.valueOf(productPrice));
            productSupplierEditText.setText(productSupplier);
            productQuantityTextView.setText(String.valueOf(productQuantity));
            productImageView.setImageBitmap(getBitmapFromUri(imageUri));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productNameEditText.setText("");
        productPriceEditText.setText("");
        productSupplierEditText.setText("");
        productQuantityTextView.setText("0");
    }

    public void openImageSelector() {
        Intent intent;

        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }

        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code READ_REQUEST_CODE.
        // If the request code seen here doesn't match, it's the response to some other intent,
        // and the below code shouldn't run at all.

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.  Pull that uri using "resultData.getData()"

            if (resultData != null) {
                imageUri = resultData.getData();
                productImageView.setImageBitmap(getBitmapFromUri(imageUri));
            }
        }
    }

    public Bitmap getBitmapFromUri(Uri uri) {

        if (uri == null || uri.toString().isEmpty())
            return null;

        // Get the dimensions of the View
        int targetW = 150;
        int targetH = 150;

        InputStream input = null;
        try {
            input = this.getContentResolver().openInputStream(uri);

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, bmOptions);
            input.close();

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            input = this.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, bmOptions);
            input.close();
            return bitmap;

        } catch (FileNotFoundException fne) {
            Log.e(LOG_TAG, "Failed to load image.", fne);
            return null;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to load image.", e);
            return null;
        } finally {
            try {
                input.close();
            } catch (IOException ioe) {

            }
        }
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the product.
                deleteProduct();
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the product.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
