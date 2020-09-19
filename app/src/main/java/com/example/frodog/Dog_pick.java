package com.example.frodog;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Dog_pick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_pick);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("추천");
    }
}
