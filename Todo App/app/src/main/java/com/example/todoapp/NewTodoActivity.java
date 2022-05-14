package com.example.todoapp;

import static com.example.todoapp.MainActivity.EDIT_DATE_DATE;
import static com.example.todoapp.MainActivity.EDIT_DATE_HOUR;
import static com.example.todoapp.MainActivity.EDIT_DATE_MIN;
import static com.example.todoapp.MainActivity.EDIT_DATE_MONTH;
import static com.example.todoapp.MainActivity.EDIT_DATE_YEAR;
import static com.example.todoapp.MainActivity.EDIT_PRIORITY;
import static com.example.todoapp.MainActivity.EDIT_TEXT;
import static com.example.todoapp.MainActivity.EDIT_TITLE;
import static com.example.todoapp.MainActivity.NEW_TODO_EDIT_REQUEST_CODE;
import static com.example.todoapp.R.color.buttonTintRed;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Calendar;

public class NewTodoActivity extends AppCompatActivity {

    public static final String REPLY_TITLE = "com.example.android.todolistsql.TITLE";
    public static final String REPLY_TEXT = "com.example.android.todolistsql.DESCRIPTION";
    public static final String DATE_YEAR = "com.example.android.todolistsql.YEAR";
    public static final String DATE_MONTH = "com.example.android.todolistsql.MONTH";
    public static final String DATE_DATE = "com.example.android.todolistsql.DATE";
    public static final String DATE_HOUR = "com.example.android.todolistsql.HOUR";
    public static final String DATE_MIN = "com.example.android.todolistsql.MINUTE";
    public static final String PRIORITY = "com.example.android.todolistsql.PRIORITY";

    private EditText mEditTitleView;
    private EditText mEditTodoView;
    private EditText mEditDateView;
    private EditText mEditTimeView;
    private RadioButton mHighPriority;
    private RadioButton mMediumPriority;

    int mYear,mMonth,mDay,mHour,mMinute;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Intent requestIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        requestIntent = getIntent();
        mEditTitleView = findViewById(R.id.edit_title);
        mEditTodoView = findViewById(R.id.edit_word);
        mEditDateView = findViewById(R.id.edit_date);
        mEditTimeView = findViewById(R.id.edit_time);
        mHighPriority = findViewById(R.id.radio_high);
        mMediumPriority = findViewById(R.id.radio_medium);

        if(requestIntent.getIntExtra("request",0)== NEW_TODO_EDIT_REQUEST_CODE){
            setUpEditText();
        }
        else{
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
        }



        datePickerDialog = new DatePickerDialog(this,
                (view1, year, monthOfYear, dayOfMonth) -> {
                    mEditDateView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    datePickerDialog.dismiss();
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        timePickerDialog = new TimePickerDialog(this,
                (view12, hourOfDay, minute) -> {
                    mEditTimeView.setText(hourOfDay + ":" + minute);
                    mHour = hourOfDay;
                    mMinute = minute;
                    timePickerDialog.dismiss();
                }, mHour, mMinute, true);


        //Removes the Action Bar on top
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        mEditDateView.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Launch Date Picker Dialog
                datePickerDialog.show();
            }
            return true;
        });

        mEditTimeView.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Launch Time Picker Dialog
                timePickerDialog.show();
            }
            return true;
        });



        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            if (checkAllFilled()) {
                setResult(RESULT_CANCELED, requestIntent);
            } else{
                addIntentReply(requestIntent);
                setResult(RESULT_OK, requestIntent);
            }
            finish();
        });
    }

    private void setUpEditText() {
        mEditTitleView.setText(requestIntent.getStringExtra(EDIT_TITLE));
        mEditTodoView.setText(requestIntent.getStringExtra(EDIT_TEXT));
        mYear=requestIntent.getIntExtra(EDIT_DATE_YEAR,0);
        mMonth=requestIntent.getIntExtra(EDIT_DATE_MONTH,0);
        mDay=requestIntent.getIntExtra(EDIT_DATE_DATE,0);
        mHour=requestIntent.getIntExtra(EDIT_DATE_HOUR,0);
        mMinute=requestIntent.getIntExtra(EDIT_DATE_MIN,0);
        mEditDateView.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
        mEditTimeView.setText(mHour + ":" + mMinute);
        int priority = requestIntent.getIntExtra(EDIT_PRIORITY,3);
        if (priority == 1){
            mHighPriority.setChecked(true);
        }
        else if (priority == 2){
            mMediumPriority.setChecked(true);
        }
        else {
        }

    }

    private boolean checkAllFilled() {
        boolean check =  TextUtils.isEmpty(mEditTitleView.getText());
        check = check || TextUtils.isEmpty(mEditTodoView.getText());
        check = check || TextUtils.isEmpty(mEditDateView.getText());
        check = check || TextUtils.isEmpty(mEditTimeView.getText());
        return check;
    }

    private void addIntentReply(Intent replyIntent) {
        String title = mEditTitleView.getText().toString();
        String todo = mEditTodoView.getText().toString();

        if (mHighPriority.isChecked()){
            replyIntent.putExtra(PRIORITY, 1);
        }
        else if (mMediumPriority.isChecked()){
            replyIntent.putExtra(PRIORITY, 2);
        }
        else {
            replyIntent.putExtra(PRIORITY, 3);
        }
        replyIntent.putExtra(REPLY_TITLE, title);
        replyIntent.putExtra(REPLY_TEXT, todo);
        replyIntent.putExtra(DATE_DATE, mDay);// Integer.parseInt(dd));
        replyIntent.putExtra(DATE_MONTH, mMonth);// (Integer.parseInt(month) - 1) );
        replyIntent.putExtra(DATE_YEAR, mYear);// Integer.parseInt(year));
        replyIntent.putExtra(DATE_HOUR, mHour);// Integer.parseInt(hour));
        replyIntent.putExtra(DATE_MIN, mMinute);// Integer.parseInt(min));
    }

}