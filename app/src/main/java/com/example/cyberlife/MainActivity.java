package com.example.cyberlife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.cyberlife.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

        //TODO: autoGenerateButton
        binding.restart.setOnClickListener(view -> {
            ClickRepository.getInstance().setRestart(true);
        });
        binding.boostButton.setOnClickListener(view -> {
            //DrawView dv = (DrawView) view;
            DrawThread.boost();
        });
        binding.pauseButton.setOnClickListener(view -> {
            DrawThread.pause();
        });
        binding.saveButton.setOnClickListener(view -> {
            short[] saveCode = CodeRepository.getInstance().getCode();

            File file;
            try {
                file = new File(this.getFilesDir(), "code_.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                // Открытие потока для записи в файл
                FileOutputStream outputStream = new FileOutputStream(file, true);

                // Записываем строку "текст" в файл
                String data = "";
                for (short sh : saveCode) {
                    data += sh + " ";

                }
                data += "\n";
                outputStream.write(data.getBytes());

                // Закрытие потока записи
                outputStream.close();

            } catch (FileNotFoundException e) {
                //Toast.makeText(context, "Ошибка FileNotFound", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            } catch (IOException e) {
                //Toast.makeText(this, "error ?", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }
        });
        binding.loadButton.setOnClickListener(view -> {
            //TODO
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ClickRepository.getInstance().setClickX(new Integer(Math.round(event.getX())));
        ClickRepository.getInstance().setClickY(new Integer(Math.round(event.getY())));
        //System.out.println(new Integer(Math.round(event.getX())));
        return false;
    }
}