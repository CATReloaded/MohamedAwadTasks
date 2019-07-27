package com.andalus.mohamedawadtasks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.textView)
    TextView textView;

    private String text =null;
    private final  static String ON_SAVE_KEY = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(savedInstanceState != null){
            text = savedInstanceState.getString(ON_SAVE_KEY);
            textView.setText(text);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(editText.getText())){
                    Toast.makeText(MainActivity.this, "Add Text First :)", Toast.LENGTH_SHORT).show();
                }else{
                    text = editText.getText().toString().trim();
                    textView.setText(text);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle save) {
        super.onSaveInstanceState(save);
        save.putString(ON_SAVE_KEY,text);
    }

}
