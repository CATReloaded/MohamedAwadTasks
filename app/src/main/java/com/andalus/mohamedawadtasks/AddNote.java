package com.andalus.mohamedawadtasks;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.andalus.mohamedawadtasks.Repositories.ConstantsRepository;
import com.andalus.mohamedawadtasks.ViewModel.NotesViewModel;
import com.andalus.mohamedawadtasks.database.NoteEntity;

public class AddNote extends AppCompatActivity {

    private Spinner spinner;
    private TextInputEditText subject;
    private TextInputEditText address;
    private int insert;
    Bundle x ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        address = findViewById(R.id.edtAddress);
        subject = findViewById(R.id.edtSubject);
        setupSpinner();

        x= getIntent().getExtras();
        insert = x.getInt("insert");
        if (insert == 1) {
            address.setText(x.getString("HEAD"));
            subject.setText(x.getString("SUBJECT"));

            switch (x.getString("PRIORITY")) {
                case "High":
                    spinner.setSelection(0);
                    break;
                case "Medium":
                    spinner.setSelection(1);
                    break;
                case "Low":
                    spinner.setSelection(2);
            }
        }
    }

    private void setupSpinner() {
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.spinnerItems));
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }


    private void saveOrUpdateNote(int insert) {
        String subjectString = subject.getText().toString().trim();
        String addressString = address.getText().toString().trim();
        String priorityString = spinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(subjectString) || TextUtils.isEmpty(addressString)) {
            Toast.makeText(this, "Please fill up all fields!", Toast.LENGTH_SHORT).show();
        } else {
            switch (insert) {
                case 0:
                    ConstantsRepository.mViewModel.insert(new NoteEntity(addressString, subjectString, priorityString));
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    ConstantsRepository.mViewModel.update(new NoteEntity(x.getInt("ID"),addressString, subjectString, priorityString));
                    Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_items_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveItem) {
            if (insert == 0)
                saveOrUpdateNote(0);
            else
                saveOrUpdateNote(1);
        }
        return super.onOptionsItemSelected(item);
    }
}
