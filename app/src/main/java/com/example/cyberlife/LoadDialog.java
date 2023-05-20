package com.example.cyberlife;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.util.Random;

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
        setInitialData();
        recyclerView = view.findViewById(R.id.recyclerview);
        CodeAdapter.OnCodeClickListener codeClickListener = new CodeAdapter.OnCodeClickListener() {
            @Override
            public void onCodeClick(Code code, int position) {
                //Todo obrabotka nazhatiya
                BotsRepository tt = BotsRepository.getInstance();
                for (int i = 0; i < tt.getBots().size(); i++) {
                    if (tt.getBots().get(i).equals(tt.getInfoBot())){
                        Random r=new Random();
                        int col=((r.nextInt(155)+100) << 24)+((r.nextInt(255)) << 16)+(r.nextInt(255) << 8)+r.nextInt(255);
                        Bot t = new Bot(tt.getInfoBot().getX(),tt.getInfoBot().getY(), code.getCode(), col);
                        ArrayList<Bot> tr = new ArrayList<>();
                        tr=BotsRepository.getInstance().getBots();
                        tr.set(i,t);
                        tt.setBots(tr);
                        BotsRepository.getInstance().setInfoBot(tt.getBots().get(i));
                        Toast.makeText(getContext(), R.string.Success_load,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        adapter=new CodeAdapter(getContext(), exa, codeClickListener);
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

    private void setInitialData() {
        Code tempCode = new Code();
        tempCode.setCode(new short[]{13,13,13,13,
                                     13,13,13,13,
                                     13,13,13,13,
                                     13,13,13,13});
        adapter.addCode(tempCode);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.dialog_layout, container, false);
        binding = getLayoutInflater().inflate(R.layout.dialog_layout, container, true);
        return binding;
    }
    private  ArrayList<Code> load(){
        File dir = new File("/data/data/com.example.cyberlife/files");
        File file = new File(dir,"code.txt");
        if (file.exists()) {
            try {

                FileInputStream fileInput = new FileInputStream(file);
                int content = fileInput.read();
                String text = "";
                while (content != -1) {
                    //System.out.print((char) content);
                    text += (char) content;
                    content=fileInput.read();
                }
                System.out.println("============== "+text);
                String[] array = (text.replaceAll("\\D+", " ").trim()).split(" ");
                System.out.println("-------------- "+array.length);
                ArrayList<Short> a = new ArrayList<>();
                for (int i = 0; i < array.length; i++) {
                    a.add(i, Short.valueOf(array[i]));
                }
                /*System.out.println("////////////////////////");
                for (int i : a) {
                    System.out.print(i + " ");
                }
                System.out.println(a.size() + "////////////////////////");*/

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
                System.out.println(tList.size());
                return tList;
                //return new Object[]{view,adapter};
            } catch (FileNotFoundException e) {
                System.err.println("exceptions");
                throw new RuntimeException(e);

            } catch (IOException e) {
                System.err.println("exceptions");
                throw new RuntimeException(e);
            }
        }
        else {
            System.err.println("FD: file not found");
        }
        return null;
        }
}
