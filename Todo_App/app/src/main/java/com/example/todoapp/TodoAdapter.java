package com.example.todoapp;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class TodoAdapter extends ListAdapter<Todo, TodoViewHolder> {

    public TodoAdapter(@NonNull DiffUtil.ItemCallback<Todo> diffCallback){
        super(diffCallback);
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return TodoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position){
        Todo current = getItem(position);
        holder.bind(current.getTodo());
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
