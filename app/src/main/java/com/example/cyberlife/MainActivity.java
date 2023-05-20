package com.example.cyberlife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.cyberlife.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private ActivityMainBinding binding;
    private CodeRepository repositiry = CodeRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());


        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.gameView.setOnTouchListener(this);
        System.out.println();

        //TODO: autoGenerateButton +-
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
                file = new File(getFilesDir(),"code.txt");
                //file = new File("code.txt");
                if (!file.exists()) {
                    System.err.println("MA.save:file not found");
                    file.createNewFile();
                }
                if (saveCode!=null) {
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
                }
            } catch (FileNotFoundException e) {
                //Toast.makeText(context, "Ошибка FileNotFound", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            } catch (IOException e) {
                //Toast.makeText(this, "error ?", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }
        });
        binding.loadButton.setOnClickListener(view -> {
            //Dialog dialog = new Dialog();
            //dialog.onCreateView(getLayoutInflater(),this,bu)
            showDialog("load");
        });
        binding.settings.setOnClickListener(view ->{
            showDialog("settings");
        });
    }
    private void showDialog(String tag) {
        if (tag.equals("load")) {
            LoadDialog loadDialog = new LoadDialog();
            loadDialog.show(getSupportFragmentManager(), "Load");
        } else {
            SettingDialog settingDialog= new SettingDialog();
            settingDialog.show(getSupportFragmentManager(),"Settings");
        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ClickRepository.getInstance().setClickX(new Integer(Math.round(event.getX())));
        ClickRepository.getInstance().setClickY(new Integer(Math.round(event.getY())));
        //System.out.println(new Integer(Math.round(event.getX())));
        return false;
    }
}