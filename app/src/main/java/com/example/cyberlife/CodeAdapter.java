package com.example.cyberlife;

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

    private LayoutInflater inflater;
    private ArrayList<Code> codes;

    CodeAdapter(Context context, ArrayList<Code> codes) {
        this.codes = codes;
        this.inflater = LayoutInflater.from(context);
    }

    public CodeAdapter() {

    }

    @Override
    public CodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeAdapter.ViewHolder holder, int position) {
        Code code = codes.get(position);
        holder.textView.setText(code.stringCode());
    }



    @Override
    public int getItemCount() {
        return codes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.text);
        }
    }
}