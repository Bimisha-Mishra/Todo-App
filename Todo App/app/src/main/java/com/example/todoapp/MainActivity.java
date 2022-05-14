package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    //private TodoViewModel mtodoViewModel;

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private TodoAdapter adapter;
    private Button fab;

    private Todo todo;

    public static final int NEW_TODO_INSERT_REQUEST_CODE = 1;
    public static final int NEW_TODO_EDIT_REQUEST_CODE = 2;
    public static final String REQUEST_POSITION = "com.example.android.todolistsql.POSITION";
    public static final String EDIT_TITLE = "com.example.android.todolistsql.TITLE";
    public static final String EDIT_TEXT = "com.example.android.todolistsql.DESCRIPTION";
    public static final String EDIT_DATE_YEAR = "com.example.android.todolistsql.YEAR";
    public static final String EDIT_DATE_MONTH = "com.example.android.todolistsql.MONTH";
    public static final String EDIT_DATE_DATE = "com.example.android.todolistsql.DATE";
    public static final String EDIT_DATE_HOUR = "com.example.android.todolistsql.HOUR";
    public static final String EDIT_DATE_MIN = "com.example.android.todolistsql.MINUTE";
    public static final String EDIT_PRIORITY = "com.example.android.todolistsql.PRIORITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Removes the Action Bar on top
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        View checkpoint = findViewById(R.id.completeCheckpoint);
        ImageView image = findViewById(R.id.dropdown_complete);
        checkpoint.setOnClickListener( view -> {
            if(recyclerView2.getVisibility() == View.VISIBLE){
                image.setRotation(0);
                recyclerView2.setVisibility(View.GONE);
            }
            else{
                image.setRotation(90);
                recyclerView2.setVisibility(View.VISIBLE);
            }
        });




        recyclerView1 = findViewById(R.id.recyclerview1);
        adapter = new TodoAdapter(new TodoAdapter.TodoDiff(), this, Status.UNCHECKED);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper1 = new
                ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
        itemTouchHelper1.attachToRecyclerView(recyclerView1);

        recyclerView2 = findViewById(R.id.recyclerview2);
        adapter = new TodoAdapter(new TodoAdapter.TodoDiff(), this, Status.CHECKED);
        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper2 = new
                ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
        itemTouchHelper2.attachToRecyclerView(recyclerView2);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            editText(NEW_TODO_INSERT_REQUEST_CODE, new Todo() );
        });
    }

    public void editText(int RequestCode,Todo todo) {
        Intent intent = new Intent(MainActivity.this, NewTodoActivity.class);
        this.todo = todo;
        intent.putExtra(EDIT_TITLE, todo.getTitle());
        intent.putExtra(EDIT_TEXT, todo.getTodo());
        intent.putExtra(EDIT_DATE_YEAR, todo.getDueYear());
        intent.putExtra(EDIT_DATE_MONTH, todo.getDueMonth());
        intent.putExtra(EDIT_DATE_DATE, todo.getDueDate());
        intent.putExtra(EDIT_DATE_HOUR, todo.getDueHour());
        intent.putExtra(EDIT_DATE_MIN, todo.getDueMin());
        if (todo.getPriority() == Priority.FIRST) {
            intent.putExtra(EDIT_PRIORITY, 1);
        }
        else if (todo.getPriority() == Priority.SECOND){
            intent.putExtra(EDIT_PRIORITY, 2);
        }
        else {
            intent.putExtra(EDIT_PRIORITY, 3);
        }
        intent.putExtra("request", RequestCode);
        startActivityForResult(intent, RequestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            int pri = data.getIntExtra(NewTodoActivity.PRIORITY,3);
            Priority priority;
            if (pri == 1)
                priority = Priority.FIRST;
            else if (pri == 2)
                priority = Priority.SECOND;
            else
                priority =Priority.THIRD;

            Calendar calendar = Calendar.getInstance();
            calendar.set(data.getIntExtra(NewTodoActivity.DATE_YEAR,2000),
                    data.getIntExtra(NewTodoActivity.DATE_MONTH,1),
                    data.getIntExtra(NewTodoActivity.DATE_DATE,1),
                    data.getIntExtra(NewTodoActivity.DATE_HOUR,0),
                    data.getIntExtra(NewTodoActivity.DATE_MIN,0));
            if(requestCode == NEW_TODO_INSERT_REQUEST_CODE) {
                Todo todo = new Todo(data.getStringExtra(NewTodoActivity.REPLY_TITLE),
                        data.getStringExtra(NewTodoActivity.REPLY_TEXT),
                        calendar,priority, Status.UNCHECKED);
                adapter.insert(todo);
            }
            else if(requestCode == NEW_TODO_EDIT_REQUEST_CODE) {
                todo.setTitle(data.getStringExtra(NewTodoActivity.REPLY_TITLE));
                todo.setTodo(data.getStringExtra(NewTodoActivity.REPLY_TEXT));
                todo.setPriority(priority);
                todo.setDate(calendar);
                adapter.editItem(todo);
            }
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_SHORT).show();
        }
    }

}