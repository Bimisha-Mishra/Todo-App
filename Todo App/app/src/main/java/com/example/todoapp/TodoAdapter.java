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
        Todo current = getItem(position);
        holder.todoItemView.setText(current.getTodo());
        holder.todoItemView.setChecked(check_status(current.getStatus()));
        holder.todoItemView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                current.setStatus(1);
                //mtodoViewModel.updateStatus(current.getId(), 1);
            } else {
                current.setStatus(0);
               //mtodoViewModel.updateStatus(current.getId(), 0);
            }
            mtodoViewModel.update(current);
        });
    }


    private static boolean check_status(int status){
        return status == 1;
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
        activity.editText(activity.NEW_TODO_EDIT_REQUEST_CODE, position, current.getTodo());
        notifyItemChanged(position);
    }

    public void editItem(int position, String text) {
        Todo current = getItem(position);
        current.setTodo(text);
        mtodoViewModel.update(current);
        notifyItemChanged(position);
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
