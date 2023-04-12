package com.example.cyberlife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cyberlife.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityMainBinding.inflate(getLayoutInflater());

        
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.boostButton.setOnClickListener(view -> {System.out.println("errrrrrrrrrrrrrrrrrrrrrrrrrr");
            //DrawView dv = (DrawView) view;
            DrawThread.boost();
        });
        binding.pauseButton.setOnClickListener(view -> {System.out.println("errrrrrrrrrrrrrrrrrrrrrrrrrr");
            DrawThread.pause();
        });
    }
}