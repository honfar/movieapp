package com.example.alec.movieapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String[] movies = {"Pootie Tang", "I Love You Man", "Step Brothers", "The Incredibles", "Team America: World Police"};
    private static final String[] ids = {"tt0258038", "tt1155056", "tt0838283", "tt0317705", "tt0372588"};
    private ListView listview;
    private ArrayList<String> movieTitles;
    private ArrayList<String> idsList;
    private AlertDialog alert;
    private AlertDialog swiped;
    private int storePosition;
    private SharedPreferences p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieTitles = new ArrayList<String>();
        idsList = new ArrayList<String>();
        p = getPreferences(Context.MODE_PRIVATE);



        //Swipe builder

        AlertDialog.Builder swipeBuilder = new AlertDialog.Builder(this);
        swipeBuilder.setTitle("Swipe Activated");
        swipeBuilder.setMessage("You just swiped, which changed the color");


        swiped = swipeBuilder.create();

        //Swipe builder end

        //Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                movieTitles.remove(storePosition);
                idsList.remove(storePosition);
                SharedPreferences.Editor e = p.edit();
                System.out.println("MOVIE" + storePosition);
                e.remove("MOVIE" + storePosition);
                e.remove("CODE" + storePosition);
                e.putInt("EXTRAMOVIES", p.getInt("EXTRAMOVIES", 0)-1);
                e.apply();

                Map<String, ?> map = p.getAll();

                System.out.println("++++++++++++++++++++++" + map);

                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_view, movieTitles);
                listview.setAdapter(adapter);
            }

        });
        builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setTitle("Removin' a Movie");
        builder.setMessage("Do you really want to remove this movie from this list? This is irreversible!");
        alert = builder.create();


        //Builder End


        for(int i = 0; i<movies.length; i++) {
            movieTitles.add(movies[i]);
            idsList.add(ids[i]);
        }



        for(int i = 5; i < p.getInt("EXTRAMOVIES", 0)+5; i++){
            movieTitles.add(p.getString("MOVIE"+i, "error"));
            idsList.add(p.getString("CODE"+i, "error"));
        }


        Map<String, ?> map = p.getAll();

        System.out.println("++++++++++++++++++++++" + map);

        listview = findViewById(R.id.movie_list);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, movieTitles);
        listview.setAdapter(adapter);



        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/" + idsList.get(position)));
                        startActivity(i);
                    }
                }
        );

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                storePosition = position;
                alert.show();
                return true;
            }
        });


    }


    /*public boolean onTouchEvent(MotionEvent event){

        swiped.show();
        return true;
    } */

    public void buttonPressed(View v){
        Intent i = new Intent(this, MovieAdd.class);
        startActivityForResult(i, 1);
    }

    @Override
    public void onStop(){
        super.onStop();
        SharedPreferences.Editor e = p.edit();
        for(int i = 5; i < movieTitles.size(); i++){
        e.putString("MOVIE" + i, movieTitles.get(i));
        e.putString("CODE" + i, idsList.get(i));
        e.putInt("EXTRAMOVIES", movieTitles.size()-5);
        }
        e.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int ResultCode, Intent data){
        movieTitles.add(data.getStringExtra("TITLE"));
        idsList.add(data.getStringExtra("CODE"));
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, movieTitles);
        listview.setAdapter(adapter);

    }
}
