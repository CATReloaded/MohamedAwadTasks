package com.andalus.mohamedawadtasks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {

    private RecyclerViewAdapter recyclerViewAdapter;
    private AppData[] fakeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycleView);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                3, GridLayoutManager.VERTICAL,
                false));

         fakeData = new AppData[]{
                new AppData("Empires & Puzzles: RPG Quest",77,R.drawable.one, "App Information hehe!"),
                new AppData("Sniper Arena: PvP Army Shooter",85,R.drawable.two , "App Information hehe!"),
                new AppData("THE ALCHEMIST CODE",43,R.drawable.three , "App Information hehe!"),
                new AppData("Tiles Hop: Forever Dancing Ball",33,R.drawable.four , "App Information hehe!"),
                new AppData("Hungry Shark Evolution",97,R.drawable.five , "App Information hehe!"),
                new AppData("Will Hero",74,R.drawable.six , "App Information hehe!"),
                new AppData("Rolling Sky",68,R.drawable.seven ,"App Information hehe!"),
                new AppData("Cooking Diary®: Best Tasty Restaurant & Cafe Game",305,R.drawable.eight ,"App Information hehe!"),
                new AppData("Disc Pool Carrom",21,R.drawable.nine ,"App Information hehe!")
        };

        recyclerViewAdapter = new RecyclerViewAdapter(fakeData);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(MainActivity.this);
    }

    @Override
    public void onItemClick(int position) {
        AppData d = fakeData[position];
        Intent intent = new Intent(MainActivity.this , AppDetails.class);
        intent.putExtra("Name" , d.getAppName());
        intent.putExtra("Size" , d.getAppSize());
        intent.putExtra("Image" , d.getAppImage());
        intent.putExtra("Description" , d.getAppDescription());
        startActivity(intent);
    }
}
