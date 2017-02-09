package com.example.momintariq.booklisting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();

    private String searchParameter;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.search_box);
        button = (Button)findViewById(R.id.submit_button);

        // Start the BookListingActivity activity using input from EditText as search parameter
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchParameter = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), BookListingActivity.class);
                intent.putExtra("searchParameter", searchParameter);
                startActivity(intent);
            }
        });
    }
}
