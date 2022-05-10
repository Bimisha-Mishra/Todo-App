package com.example.todoapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Todo list);

    @Query("DELETE FROM todo_table WHERE list = :id")
    void deleteTodo(int id);

    @Query("UPDATE todo_table SET mTodo = :texts  WHERE list = :id")
    void updateTodolist(int id, String texts);

    @Query("UPDATE todo_table SET status = :checked  WHERE list = :id")
    void updateStatus(int id, int checked);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("SELECT * FROM todo_table ORDER BY list ASC")
    LiveData<List<Todo>> getAlphabetizedWords();
}
