package com.example.todoapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private final TodoRepository mRepository;
    private final LiveData<List<Todo>> mTodoList;
    private final LiveData<List<Todo>> mCompletedList;

    public TodoViewModel (Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mTodoList = mRepository.getTodoList();
        mCompletedList = mRepository.getCompletedList();
    }

    LiveData<List<Todo>> getTodoList(Status status) {
        if (status == Status.UNCHECKED){
            return mTodoList;
        }
        else {
           return mCompletedList;
        }
    }

    public void insert(Todo todo) { mRepository.insert(todo);}

    public void delete(Todo todo) { mRepository.delete(todo);}

    public void update(Todo todo) {
        mRepository.update(todo);
    }
}
