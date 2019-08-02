package com.andalus.mohamedawadtasks;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.andalus.mohamedawadtasks.RecyclerView.RecyclerAdapter;
import com.andalus.mohamedawadtasks.database.DatabaseApp;
import com.andalus.mohamedawadtasks.database.UsersEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddUsers extends AppCompatActivity {

    @BindView(R.id.edtName)
    TextInputEditText edtName;
    @BindView(R.id.edtAge)
    TextInputEditText edtAge;
    @BindView(R.id.genderSpinner)
    Spinner genderSpinner;
    @BindView(R.id.eyeColorSpinner)
    Spinner eyeColorSpinner;

    String gender;
    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);
        ButterKnife.bind(this);
        setupEyeColorSpinner();
        setupGenderSpinner();
    }

    void setupEyeColorSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item
                ,getResources().getStringArray(R.array.ColorArray));
        eyeColorSpinner.setAdapter(adapter);
        eyeColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        color = "Black";
                        break;
                    case 1:
                        color = "Blue";
                        break;
                    case 2:
                        color = "Green";
                        break;
                    case 3:
                        color = "Brown";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    void setupGenderSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item
                ,getResources().getStringArray(R.array.GenderArray));
        genderSpinner.setAdapter(adapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        gender = "Male";
                        break;
                    case 1:
                        gender = "Female";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_items_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveItem) {
            saveDate();
        }
        return true;
    }

    void saveDate(){
        String name = edtName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        if(name.isEmpty() || age.isEmpty() || gender.isEmpty() || color.isEmpty()){
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
        }else{
            DatabaseApp db = DatabaseApp.getInstance(this);
            db.usersDao().insertAll(new UsersEntity(name,gender,age,color));
            finish();
        }
    }




}
