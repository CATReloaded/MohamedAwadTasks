package com.andalus.mohamedawadtasks;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andalus.mohamedawadtasks.mediaPlayer.ListOfMp3FromStorage;
import com.andalus.mohamedawadtasks.services.MediaPlayerService;

import static com.andalus.mohamedawadtasks.mediaPlayer.ListOfMp3FromStorage.mp3Files;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

    }

    private void setupRecycler() {
        ListOfMp3FromStorage.requestRead(this);
        RecyclerView recyclerView = findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter(this, mp3Files);
        recyclerView.setAdapter(rvAdapter);
        rvAdapter.setOnItemClickListener(new RecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Intent intent = new Intent(MainActivity.this, MediaPlayerService.class);
                intent.setAction(MediaPlayerService.RUN_MEDIA_PLAYER);
                MediaPlayerService.setPosition(pos);
                startService(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupRecycler();
        } else {
            checkPermission();
        }


    }


    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this
                    , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , 1);
        } else {
            setupRecycler();
        }
    }


}


