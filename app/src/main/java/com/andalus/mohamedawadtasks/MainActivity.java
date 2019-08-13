package com.andalus.mohamedawadtasks;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.andalus.mohamedawadtasks.RecyclerView.RecyclerViewAdapter;
import com.andalus.mohamedawadtasks.Repositories.ConstantsRepository;
import com.andalus.mohamedawadtasks.ViewModel.NotesViewModel;
import com.andalus.mohamedawadtasks.database.NoteEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fab = findViewById(R.id.fab);


        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL
                , false));
        recyclerView.setAdapter(recyclerViewAdapter);

        ConstantsRepository.mViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        ConstantsRepository.mViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                recyclerViewAdapter.setWord(noteEntities);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddNote.class);
                i.putExtra("insert",0);
                startActivity(i);
            }
        });

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Intent i = new Intent(MainActivity.this,AddNote.class);
                NoteEntity word = recyclerViewAdapter.getWordAtPosition(position);
                i.putExtra("HEAD",word.getHead());
                i.putExtra("SUBJECT",word.getSubject());
                i.putExtra("PRIORITY",word.getPriority());
                i.putExtra("ID",word.getId());
                i.putExtra("insert",1);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAll:
            ConstantsRepository.mViewModel.deleteAllNotes();
            Toast.makeText(this, "All Notes Deleted!", Toast.LENGTH_SHORT).show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
