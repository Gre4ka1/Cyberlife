package com.example.cyberlife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Dialog extends DialogFragment {
    private RecyclerView recyclerView;
    private CodeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        File file = new File("code_.txt");
        if (file.exists()) {
            try {
                FileInputStream fileInput = new FileInputStream(file);
                int content;
                String text = "";
                while ((content = fileInput.read()) != -1) {
                    System.out.print((char)content);
                    text+=(char)content;
                }
                String[] array = (text.replaceAll("\\D+", " ").trim()).split(" ");
                int[] a = new int[array.length];
                for (int i = 0; i < array.length; i++) {
                    a[i] = Integer.valueOf(array[i]);
                }




                fileInput.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        adapter = new CodeAdapter(getContext(),);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
