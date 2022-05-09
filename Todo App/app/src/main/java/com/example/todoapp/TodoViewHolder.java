package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    private final TextView todoItemView;

    private TodoViewHolder(View itemView){
        super(itemView);
        todoItemView = itemView.findViewById(R.id.checkbox);
    }

    public void bind(String text) {
        todoItemView.setText(text);
    }

    static TodoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new TodoViewHolder(view);
    }
}
