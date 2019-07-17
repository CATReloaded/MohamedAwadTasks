package com.andalus.mohamedawadtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppDetails extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView appImage;
    @BindView(R.id.name)
    TextView appName;
    @BindView(R.id.size)
    TextView appSize;
    @BindView(R.id.desc)
    TextView appDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String name = bundle.getString("Name");
            int size = bundle.getInt("Size");
            int image = bundle.getInt("Image");
            String description = bundle.getString("Description");
            appName.setText(name);
            appSize.setText(String.valueOf(size) + " Mb");
            appDesc.setText(description);
            appImage.setImageResource(image);


        }
    }
}
