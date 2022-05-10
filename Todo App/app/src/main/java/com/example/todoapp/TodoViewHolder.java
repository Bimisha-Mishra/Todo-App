package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    public final CheckBox todoItemView;

    private TodoViewHolder(View itemView){
        super(itemView);
        todoItemView = itemView.findViewById(R.id.checkbox);
    }





    static TodoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new TodoViewHolder(view);
    }
}
