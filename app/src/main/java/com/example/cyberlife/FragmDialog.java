package com.example.cyberlife;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyberlife.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FragmDialog extends DialogFragment {
    private RecyclerView recyclerView;
    private CodeAdapter adapter;

    /*@NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.dialog_layout, , false);
        recyclerView = view.findViewById(R.id.recyclerview);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle("Диалоговое окно").setView(R.layout.dialog_layout).create();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        File file = new File("code.txt");
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
                ArrayList<Short> a = new ArrayList<>();
                for (int i = 0; i < array.length; i++) {
                    a.set(i, Short.valueOf(array[i]));
                }
                System.out.println("////////////////////////");
                for (int i:a) {
                    System.out.print(i+" ");
                }
                System.out.println(a.size()+"////////////////////////");

                if (a.size()%16!=0){
                    System.err.println("codeFile length error");
                }
                ArrayList<Code> tList= new ArrayList<>();
                while(a.size()>=16){
                    Code t = new Code();
                    short[] tt = new short[16];
                    for (int i = 0; i < 16; i++) {
                        tt[i]= a.get(0);
                        a.remove(0);
                    }
                    t.setCode(tt);
                    tList.add(t);
                }
                adapter = new CodeAdapter(getContext(),tList);
                recyclerView.setAdapter(adapter);

                fileInput.close();
                return view;
            } catch (FileNotFoundException e) {
                System.err.println("exceptions");
                throw new RuntimeException(e);

            } catch (IOException e) {
                System.err.println("exceptions");
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("FD.create dialog:file not found");
        }
        System.err.println("return null");
        return null;
    }
}
