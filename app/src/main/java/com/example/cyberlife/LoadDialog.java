package com.example.cyberlife;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LoadDialog extends DialogFragment {
    private RecyclerView recyclerView;
    private CodeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter(new CodeAdapter(getActivity(), new ArrayList<>()));
        System.out.println("111111111111111111111111111111111");
        if (ContextCompat.checkSelfPermission(getContext(), "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            System.out.println("permission is");

            Object[] a=load(view);
            CodeAdapter adapter1=(CodeAdapter)a[1];

            System.out.println(adapter1.getCodes().toString());
            recyclerView.setAdapter((RecyclerView.Adapter) a[1]);
        } else {
            System.out.println("2222222222222222222222222");
            ActivityResultLauncher<String> requestPermissionLauncher =
                    registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {

                        if (isGranted) {
                            System.out.println("permission granted");
                            Object[] a=load(view);
                            recyclerView.setAdapter((RecyclerView.Adapter) a[1]);
                        } else {
                            System.out.println("permission denied");
                        }

                    });
            requestPermissionLauncher.launch("da");
        }

        return view;
    }
        Object[] load(View view){
        File file = new File("code.txt");
        if (file.exists()) {
            try {

                FileInputStream fileInput = new FileInputStream(file);
                int content;
                String text = "";
                while ((content = fileInput.read()) != -1) {
                    System.out.print((char) content);
                    text += (char) content;
                }
                String[] array = (text.replaceAll("\\D+", " ").trim()).split(" ");
                ArrayList<Short> a = new ArrayList<>();
                for (int i = 0; i < array.length; i++) {
                    a.set(i, Short.valueOf(array[i]));
                }
                System.out.println("////////////////////////");
                for (int i : a) {
                    System.out.print(i + " ");
                }
                System.out.println(a.size() + "////////////////////////");

                if (a.size() % 16 != 0) {
                    System.err.println("codeFile length error");
                }
                ArrayList<Code> tList = new ArrayList<>();
                while (a.size() >= 16) {
                    Code t = new Code();
                    short[] tt = new short[16];
                    for (int i = 0; i < 16; i++) {
                        tt[i] = a.get(0);
                        a.remove(0);
                    }
                    t.setCode(tt);
                    tList.add(t);

                }

                adapter = new CodeAdapter(getContext(), tList);
                //recyclerView.setAdapter(adapter);

                fileInput.close();
                return new Object[]{view,adapter};
            } catch (FileNotFoundException e) {
                System.err.println("exceptions");
                throw new RuntimeException(e);

            } catch (IOException e) {
                System.err.println("exceptions");
                throw new RuntimeException(e);
            }
        } else System.err.println("FD: file not found");
            return null;
        }
}
