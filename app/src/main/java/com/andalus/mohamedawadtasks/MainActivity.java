package com.andalus.mohamedawadtasks;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andalus.mohamedawadtasks.RecyclerView.RecyclerAdapter;
import com.andalus.mohamedawadtasks.database.DatabaseApp;
import com.andalus.mohamedawadtasks.database.UsersEntity;
import com.andalus.mohamedawadtasks.preference.PreferenceSettings;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseApp db;
    FloatingActionButton fab;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    TextView emptyText;
    List<UsersEntity> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyText = findViewById(R.id.emptyText);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddUsers.class));
            }
        });
        setupRecyclerView();
        checkData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //setupRecyclerView();
        updateRecyclerView();
        checkData();

    }

    void updateRecyclerView() {
        if (users != null) {
            users = db.usersDao().getAll();
            recyclerAdapter.setData(users);
        }
    }

    void checkData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String val = sharedPreferences.getString("eyeColor", "All");
        db = DatabaseApp.getInstance(this);
        if (val.equals("All")) {
            users = db.usersDao().getAll();
            if (users.size() < 1) {
                recyclerView.setVisibility(View.GONE);
                emptyText.setVisibility(View.VISIBLE);
                emptyText.setText("No users yet... ");
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyText.setVisibility(View.GONE);
            }
        } else {
            users = db.usersDao().findByEyeColor(val);
            if (users.size() < 1) {
                recyclerView.setVisibility(View.GONE);
                emptyText.setVisibility(View.VISIBLE);
                emptyText.setText("No users with " + val + " eye color..");
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyText.setVisibility(View.GONE);
            }
        }
        recyclerAdapter.setData(users);


//        switch (val) {
//            case "All":
//                users = db.usersDao().getAll();
//                if (users.size() < 1) {
//                    recyclerView.setVisibility(View.GONE);
//                    emptyText.setVisibility(View.VISIBLE);
//                    emptyText.setText("No users yet... ");
//                } else {
//                    recyclerView.setVisibility(View.VISIBLE);
//                    emptyText.setVisibility(View.GONE);
//                }
//                break;
//            case "Blue":
//            case "Green":
//            case "Black":
//            case "Brown":
//                users = db.usersDao().findByEyeColor(val);
//                if (users.size() <= 1) {
//                    recyclerView.setVisibility(View.GONE);
//                    emptyText.setVisibility(View.VISIBLE);
//                    emptyText.setText("No users with " + val + " color..");
//                } else {
//                    recyclerView.setVisibility(View.VISIBLE);
//                    emptyText.setVisibility(View.GONE);
//                }
//
//        }
    }

    void setupRecyclerView() {
        recyclerAdapter = new RecyclerAdapter(users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL
                , false));
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this
                , DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(MainActivity.this, PreferenceSettings.class));
        } else if (item.getItemId() == R.id.delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete All Users")
                    .setMessage("Are you sure you want to delete all users?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.usersDao().deleteAll();
                            //setupRecyclerView();
                            updateRecyclerView();
                            checkData();
                            Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        return true;
    }
}
