package com.example.todoapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {
    private TodoDao mTodoDao;
    private LiveData<List<Todo>> mTodoLists;

    TodoRepository(Application application){
        TodoDatabase db = TodoDatabase.getDatabase(application);
        mTodoDao = db.todoDao();
        mTodoLists = mTodoDao.getAlphabetizedWords();
    }

    LiveData<List<Todo>> getTodoLists(){
        mTodoLists = mTodoDao.getAlphabetizedWords();
        return mTodoLists;
    }

    void insert(Todo todo){
        TodoDatabase.databaseWriteExecutor.execute(() -> {
            mTodoDao.insert(todo);
        });
    }

    void delete(int id){
        TodoDatabase.databaseWriteExecutor.execute(() -> {
            mTodoDao.deleteTodo(id);
        });
    }

    void updateStatus(int id, int stat){
        TodoDatabase.databaseWriteExecutor.execute(() -> {
            mTodoDao.updateStatus(id,stat);
        });
    }
}
