package com.example.todoapp;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "Todo_table")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "list")
    private int id;
    private String Title;
    private String Todo;
    private int dueYear;
    private int dueMonth;
    //private int dueDay;
    private int dueDate;
    private int dueHour;
    private int dueMin;
    private Priority priority;
    private Status status;

    public Todo(){}

    public Todo(@NonNull String title,@NonNull String todo,@NonNull Calendar date,@NonNull Priority priority,@NonNull Status status) {
        Title = title;
        Todo = todo;
        setDate(date);
        this.priority = priority;
        this.status = status;
    }

    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}

    public String getTodo(){return this.Todo;}
    public void setTodo(String todo){this.Todo = todo;}

    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}

    public String getTitle() { return Title; }
    public void setTitle(String title) { Title = title; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public int getDueYear() {return dueYear;}
    public void setDueYear(int dueYear) {this.dueYear = dueYear;}

    public int getDueMonth() {return dueMonth;}
    public void setDueMonth(int dueMonth) {this.dueMonth = dueMonth;}

    /*public int getDueDay() {return dueDay;}
    public void setDueDay(int dueDay) {this.dueDay = dueDay;}*/

    public int getDueHour() {return dueHour;}
    public void setDueHour(int dueHour) {this.dueHour = dueHour;}

    public int getDueMin() {return dueMin;}
    public void setDueMin(int dueMin) {this.dueMin = dueMin;}

    public int getDueDate() {return dueDate;}
    public void setDueDate(int dueDate) {this.dueDate = dueDate;}

    public void setDate(Calendar date) {
        this.dueYear = date.get(Calendar.YEAR);
        this.dueMonth = date.get(Calendar.MONTH);
        this.dueDate = date.get(Calendar.DAY_OF_MONTH);
        this.dueHour = date.get(Calendar.HOUR_OF_DAY);
        this.dueMin = date.get(Calendar.MINUTE);
    }
}
