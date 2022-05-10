package com.example.todoapp;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.List;

public class TodoAdapter extends ListAdapter<Todo, TodoViewHolder> {

    private  List<Todo> todoList;
    private final MainActivity activity;
    private final TodoViewModel mtodoViewModel;

    public TodoAdapter(@NonNull DiffUtil.ItemCallback<Todo> diffCallback, MainActivity ACT ){
        super(diffCallback);
        this.activity = ACT;

        mtodoViewModel = new ViewModelProvider(this.activity).get(TodoViewModel.class);
        mtodoViewModel.getTodoLists().observe(this.activity, this::submitList);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return TodoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo current = todoList.get(position);
        //holder.bind(current);
        //Log.println(Log.INFO,"message", current.getTodo());
        holder.todoItemView.setText(current.getTodo());
        holder.todoItemView.setChecked(check_status(current.getStatus()));
        holder.todoItemView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mtodoViewModel.updateStatus(current.getId(), 1);
            } else {
                mtodoViewModel.updateStatus(current.getId(), 0);
            }
        });
    }


    private static boolean check_status(int status){
        return status == 1;
    }

    public void setTasks(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public void editItem(int position) {
        Todo current = todoList.get(position);
    }

    public void deleteItem(int position) {

        mtodoViewModel.delete(position);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return activity;
    }

    public void insert(Todo todo) {
        mtodoViewModel.insert(todo);
    }

    public List<Todo> getAllTodo() {
        return todoList;
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
