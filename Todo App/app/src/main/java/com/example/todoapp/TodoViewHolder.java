package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    public final TextView todoTitleView;
    public final TextView todoDecriptView;
    public final TextView todoStatusView;
    public final TextView todoDateView;
    public final TextView todoMonthView;
    public final TextView todoDayView;
    public final TextView todoTimeView;


    private TodoViewHolder(View itemView){
        super(itemView);
        todoTitleView = itemView.findViewById(R.id.title);
        todoDecriptView = itemView.findViewById(R.id.description);
        todoStatusView = itemView.findViewById(R.id.status);
        todoDateView = itemView.findViewById(R.id.date);
        todoMonthView = itemView.findViewById(R.id.month);
        todoDayView = itemView.findViewById(R.id.day);
        todoTimeView = itemView.findViewById(R.id.time);

    }


    static TodoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new TodoViewHolder(view);
    }
}
