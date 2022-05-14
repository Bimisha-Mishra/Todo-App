package com.example.todoapp;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {
    private final TodoDao mTodoDao;
    private final LiveData<List<Todo>> mTodoList;
    private final LiveData<List<Todo>> mCompletedList;

    TodoRepository(Application application){
        TodoDatabase db = TodoDatabase.getDatabase(application);
        mTodoDao = db.todoDao();
        mTodoList = mTodoDao.getTodoList(Status.UNCHECKED);
        mCompletedList = mTodoDao.getTodoList(Status.CHECKED);
    }

    LiveData<List<Todo>> getTodoList(){
        return mTodoList;
    }
    LiveData<List<Todo>> getCompletedList(){
        return mCompletedList;
    }

    void insert(Todo todo){
        TodoDatabase.databaseWriteExecutor.execute(() -> mTodoDao.insert(todo));
    }

    void delete(Todo todo){
        TodoDatabase.databaseWriteExecutor.execute(() -> mTodoDao.deleteTodo(todo));
    }

    public void update(Todo todo) {
        TodoDatabase.databaseWriteExecutor.execute(() -> mTodoDao.update(todo));
    }
}
