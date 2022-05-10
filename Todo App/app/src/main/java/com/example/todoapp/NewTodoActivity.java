package com.example.todoapp;

import static com.example.todoapp.MainActivity.REQUEST_DATA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class NewTodoActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.todolistsql.REPLY";
    private EditText mEditTodoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        mEditTodoView = findViewById(R.id.edit_word);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mEditTodoView.setText(getIntent().getStringExtra(REQUEST_DATA));

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = getIntent();
            if (TextUtils.isEmpty(mEditTodoView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else{
                String todo = mEditTodoView.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, todo);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

}