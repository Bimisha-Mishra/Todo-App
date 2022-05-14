package com.example.todoapp;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TodoAdapter extends ListAdapter<Todo, TodoViewHolder> {

    private final MainActivity activity;
    private final TodoViewModel mtodoViewModel;

    public TodoAdapter(@NonNull DiffUtil.ItemCallback<Todo> diffCallback, MainActivity ACT , Status status){
        super(diffCallback);
        this.activity = ACT;
        mtodoViewModel = new ViewModelProvider(this.activity).get(TodoViewModel.class);
        mtodoViewModel.getTodoList(status).observe(this.activity, this::submitList);

    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return TodoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {

        Todo current = getItem(position);
        holder.todoTitleView.setText(current.getTitle());
        holder.todoDecriptView.setText(current.getTodo());
        holder.todoTimeView.setText(setTime(current));
        holder.todoDayView.setText(getDayName(current));
        holder.todoDateView.setText(getDueDate(current));
        holder.todoMonthView.setText(getMonthName(current));

        Drawable buttonDrawable = holder.todoStatusView.getBackground();
        if (current.getStatus() == Status.UNCHECKED){
            if (current.getPriority() == Priority.THIRD){
                DrawableCompat.setTint(buttonDrawable, activity.getResources().getColor(R.color.buttonTintGreen));
            }
            if (current.getPriority() == Priority.SECOND){
                DrawableCompat.setTint(buttonDrawable, activity.getResources().getColor(R.color.buttonTintYellow));

            }
            if (current.getPriority() == Priority.FIRST){
                DrawableCompat.setTint(buttonDrawable, activity.getResources().getColor(R.color.buttonTintRed));
            }
            holder.todoStatusView.setBackground(buttonDrawable);
            holder.todoStatusView.setText("Not Completed");
        }
        else {
            DrawableCompat.setTint(buttonDrawable, activity.getResources().getColor(R.color.colorAccent));
            holder.todoStatusView.setBackground(buttonDrawable);
            holder.todoStatusView.setText("Completed");
        }

        holder.todoStatusView.setOnClickListener((buttonView) -> {
            if (current.getStatus() == Status.UNCHECKED) {
                current.setStatus(Status.CHECKED);
            } else {
                current.setStatus(Status.UNCHECKED);
            }
            mtodoViewModel.update(current);
        });
    }

    private String getDueDate(Todo current) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        Calendar cal = Calendar.getInstance();
        cal.set(current.getDueYear(), current.getDueMonth() , current.getDueDate());
        Date date= cal.getTime();
        //specifies the pattern to print
        sdf.applyPattern("dd");
        return sdf.format(date);
    }

    public String getDayName(Todo current) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        Calendar cal = Calendar.getInstance();
        cal.set(current.getDueYear(), current.getDueMonth() , current.getDueDate());
        Date date= cal.getTime();
        //specifies the pattern to print
        sdf.applyPattern("EEE");
        return sdf.format(date);
    }
    public String getMonthName(Todo current) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        Calendar cal = Calendar.getInstance();
        cal.set(current.getDueYear(), current.getDueMonth() , current.getDueDate());
        Date date= cal.getTime();
        //specifies the pattern to print
        sdf.applyPattern("MMM");
        return sdf.format(date);
    }

    private String setTime(Todo current) {
        String h = Integer.toString(current.getDueHour());
        String m = Integer.toString(current.getDueMin());
        if (h.length() == 1) {
            h = '0' + h;
        }
        if (m.length() == 1) {
            m = '0' + m;
        }
        return h+':'+m;
    }


    public void deleteItem(int position) {
        Todo current = getItem(position);
        mtodoViewModel.delete(current);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return activity;
    }

    public void insert(Todo todo) {
        mtodoViewModel.insert(todo);
    }


    public void gotoEditActivity(int position) {
        Todo current = getItem(position);
        activity.editText(MainActivity.NEW_TODO_EDIT_REQUEST_CODE, current);
        notifyItemChanged(position);
    }

    public void editItem(Todo todo) {
        mtodoViewModel.update(todo);
        //notifyItemChanged(position);
    }

    static class TodoDiff extends DiffUtil.ItemCallback<Todo> {

        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getTodo().equals(newItem.getTodo());
        }
    }
}
