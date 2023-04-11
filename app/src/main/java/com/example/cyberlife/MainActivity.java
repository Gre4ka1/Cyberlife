package com.example.cyberlife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.gameView).setOnClickListener(view -> {
            DrawView dv = (DrawView) view;
            DrawThread.click(dv.getCx(),dv.getCy());
        });
    }
}