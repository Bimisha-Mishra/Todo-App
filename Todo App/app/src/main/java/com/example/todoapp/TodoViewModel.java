package com.example.todoapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private final TodoRepository mRepository;
    private final LiveData<List<Todo>> mTodoLists;
    public TodoViewModel (Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mTodoLists = mRepository.getTodoLists();
    }

    LiveData<List<Todo>> getTodoLists() { //mTodoLists = mRepository.getTodoLists();
        return mTodoLists;}

    public void insert(Todo todo) { mRepository.insert(todo);}

    public void delete(Todo todo) { mRepository.delete(todo);}

    public void update(Todo todo) {
        mRepository.update(todo);
    }
}
