package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Todo_table")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "list")
    private int id;
    private String mTodo;
    public int getId() {return this.id;}

    public void setId(int id) {
        this.id = id;
    }

    public Todo(@NonNull String todo) {this.mTodo = todo;}
    public String getTodo(){return this.mTodo;}
}
