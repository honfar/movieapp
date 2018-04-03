package com.example.alec.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private static final String[] movies = {"Pootie Tang", "I Love You Man", "Step Brothers", "The Incredibles", "Team America: World Police"};
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.movie_list);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, movies);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String[] movies = {"tt0258038", "tt1155056", "tt0838283", "tt0317705", "tt0372588" };
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/" + movies[position]));
                        startActivity(i);
                    }
                }
        );
    }
}
