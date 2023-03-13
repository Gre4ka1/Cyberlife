package com.example.cyberlife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawView dv = new DrawView(this);
        dv.setOnClickListener(v -> {DrawThread.click(dv.getCx(),dv.getCy());});
        setContentView(dv);
    }
}