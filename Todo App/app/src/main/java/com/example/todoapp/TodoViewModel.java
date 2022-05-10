package com.example.todoapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository mRepository;
    public LiveData<List<Todo>> mTodoLists;
    public TodoViewModel (Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mTodoLists = mRepository.getTodoLists();
    }

    LiveData<List<Todo>> getTodoLists() { mTodoLists = mRepository.getTodoLists(); return mTodoLists;}

    public void insert(Todo todo) { mRepository.insert(todo);}

    public void delete(int id) { mRepository.delete(id);}

    public void updateStatus(int id, int stat) {mRepository.updateStatus(id,stat);}
}
