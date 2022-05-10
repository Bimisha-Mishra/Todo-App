package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private TodoViewModel mtodoViewModel;

    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private FloatingActionButton fab;

    private List<Todo> todoList;

    public static final int NEW_TODO_INSERT_REQUEST_CODE = 1;
    public final int NEW_TODO_EDIT_REQUEST_CODE = 2;
    public static final String REQUEST_POSITION = "com.example.android.todolistsql.POSITION";
    public static final String REQUEST_DATA = "com.example.android.todolistsql.DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new TodoAdapter(new TodoAdapter.TodoDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            editText(NEW_TODO_INSERT_REQUEST_CODE, -1, "");
        });
    }

    public void editText(int RequestCode, int position, String todo) {
        Intent intent = new Intent(MainActivity.this, NewTodoActivity.class);
        intent.putExtra(REQUEST_POSITION, position);
        intent.putExtra(REQUEST_DATA, todo);
        startActivityForResult(intent, RequestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_TODO_INSERT_REQUEST_CODE && resultCode == RESULT_OK) {
            Todo todo = new Todo(data.getStringExtra(NewTodoActivity.EXTRA_REPLY), 0);
            adapter.insert(todo);
        }
        else if(requestCode == NEW_TODO_EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.editItem(data.getIntExtra(REQUEST_POSITION,0),
                            data.getStringExtra(NewTodoActivity.EXTRA_REPLY));
        }else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_SHORT).show();
        }
    }

}