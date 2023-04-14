package com.example.cyberlife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cyberlife.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private CodeRepositiry repositiry = CodeRepositiry.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());


        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());



        binding.boostButton.setOnClickListener(view -> {
            //DrawView dv = (DrawView) view;
            DrawThread.boost();
        });
        binding.pauseButton.setOnClickListener(view -> {
            DrawThread.pause();
        });
    }
}