package com.example.alec.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by alec on 4/3/18.
 */

public class MovieAdd extends AppCompatActivity {

    private EditText title_entry;
    private EditText code_entry;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_add);

        title_entry = findViewById(R.id.title_entry);
        code_entry = findViewById(R.id.code_entry);
    }

    @Override
    public void onBackPressed(){
        Intent data = new Intent();
        data.putExtra("TITLE", title_entry.getText().toString());
        data.putExtra("CODE", code_entry.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }

}
