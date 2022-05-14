package com.example.todoapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Todo list);

    @Delete
    void deleteTodo(Todo todo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("SELECT * FROM todo_table WHERE status = :pr ORDER BY priority ASC, dueYear ASC, dueMonth ASC, dueDate ASC, dueHour ASC, dueMin ASC")
    LiveData<List<Todo>> getTodoList(Status pr);

    @Update
    void update(Todo todo);
}
