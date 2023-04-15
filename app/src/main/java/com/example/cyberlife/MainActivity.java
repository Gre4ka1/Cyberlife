package com.example.cyberlife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.cyberlife.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    ActivityMainBinding binding;
    private CodeRepository repositiry = CodeRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());


        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.gameView.setOnTouchListener(this);
            System.out.println();




        binding.boostButton.setOnClickListener(view -> {
            //DrawView dv = (DrawView) view;
            DrawThread.boost();
        });
        binding.pauseButton.setOnClickListener(view -> {
            DrawThread.pause();
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ClickRepository.setClickX(new Integer(Math.round(event.getX())));
        ClickRepository.setClickY(new Integer(Math.round(event.getY())));
        //System.out.println(new Integer(Math.round(event.getX())));
        return false;
    }
}