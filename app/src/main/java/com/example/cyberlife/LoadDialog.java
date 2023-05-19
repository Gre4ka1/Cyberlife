package com.example.cyberlife;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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
    private View binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Code example = new Code();
        example.setCode(new short[]{1, 1, 1, 1,
                                    1, 1, 1, 1,
                                    1, 1, 1, 1,
                                    1, 1, 1, 1});
        ArrayList<Code> exa = new ArrayList<>();
        exa.add(example);
        adapter=new CodeAdapter(getContext(), exa);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter(new CodeAdapter(getActivity(), new ArrayList<>()));
        System.out.println("111111111111111111111111111111111");
        if (ContextCompat.checkSelfPermission(getContext(), "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            System.out.println("permission is");
            adapter.setCodes(load());
            recyclerView.setAdapter(adapter);
        } else {
            System.out.println("2222222222222222222222222");

            ActivityCompat.requestPermissions(this.getActivity(), new String[] {"android.permission.READ_EXTERNAL_STORAGE"},
                    1);
            //=================================
            ActivityResultLauncher<String> requestPermissionLauncher =
                    registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                        System.out.println("3333333333333333333333");
                        if (isGranted) {
                            System.out.println("permission granted");
                            adapter.setCodes(load());
                            recyclerView.setAdapter(adapter);
                        } else {
                            System.out.println("permission denied");
                        }

                    });
            requestPermissionLauncher.launch("da");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.dialog_layout, container, false);
        binding = getLayoutInflater().inflate(R.layout.dialog_layout, container, true);
        return binding;
    }
        ArrayList<Code> load(){
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

                /*adapter = new CodeAdapter(getContext(), tList);
                //recyclerView.setAdapter(adapter);
*/
                fileInput.close();
                return tList;
                //return new Object[]{view,adapter};
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
