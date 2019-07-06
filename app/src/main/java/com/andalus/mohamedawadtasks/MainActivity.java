package com.andalus.mohamedawadtasks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycleView);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                3, GridLayoutManager.VERTICAL,
                false));

        data[] fakeData = new data[]{
                new data("Empires & Puzzles: RPG Quest",77,R.drawable.one),
                new data("Sniper Arena: PvP Army Shooter",85,R.drawable.two),
                new data("THE ALCHEMIST CODE",43,R.drawable.three),
                new data("Tiles Hop: Forever Dancing Ball",33,R.drawable.four),
                new data("Hungry Shark Evolution",97,R.drawable.five),
                new data("Will Hero",74,R.drawable.six),
                new data("Rolling Sky",68,R.drawable.seven),
                new data("Cooking Diary®: Best Tasty Restaurant & Cafe Game",305,R.drawable.eight),
                new data("Disc Pool Carrom",21,R.drawable.nine)
        };

        recyclerView.setAdapter(new RecyclerViewAdapter(fakeData));
    }
}
