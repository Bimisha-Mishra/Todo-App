package com.example.todoapp;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {
    private final TodoDao mTodoDao;
    private final LiveData<List<Todo>> mTodoLists;

    TodoRepository(Application application){
        TodoDatabase db = TodoDatabase.getDatabase(application);
        mTodoDao = db.todoDao();
        mTodoLists = mTodoDao.getAlphabetizedWords();
    }

    LiveData<List<Todo>> getTodoLists(){
        return mTodoLists;
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
