package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Todo_table")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "list")
    private int id;
    private String mTodo;
    private int status;


    public Todo(@NonNull String todo, int status) {
        this.mTodo = todo;
        this.status = status;
    }

    public int getId() {return this.id;}
    public void setId(int id) {
        this.id = id;
    }

    public String getTodo(){return this.mTodo;}
    public void setTodo(String todo){this.mTodo = todo;}



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
