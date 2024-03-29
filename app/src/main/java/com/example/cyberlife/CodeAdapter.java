package com.example.cyberlife;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CodeAdapter  extends RecyclerView.Adapter<CodeAdapter.ViewHolder>{

    interface OnCodeClickListener{
        void onCodeClick(Code code, int position);
    }

    private OnCodeClickListener onCodeClickListener;

    private LayoutInflater inflater;
    private ArrayList<Code> codes;


    CodeAdapter(Context context, ArrayList<Code> codes,OnCodeClickListener clickListener) {
        this.codes = codes;
        this.inflater = LayoutInflater.from(context);
        this.onCodeClickListener=clickListener;
    }
    CodeAdapter(Context context, ArrayList<Code> codes) {
        this.codes = codes;
        this.inflater = LayoutInflater.from(context);
    }

    public CodeAdapter() {

    }
    public void addCode(Code code){
        codes.add(code);
    }
    public ArrayList<Code> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<Code> codes) {
        this.codes = codes;
    }

    @Override
    public CodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Code code = codes.get(position);
        holder.textView.setText(code.stringCode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCodeClickListener.onCodeClick(code, position);
            }
        });
    }



    @Override
    public int getItemCount() {

        return codes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        ViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.text);
        }
    }
}