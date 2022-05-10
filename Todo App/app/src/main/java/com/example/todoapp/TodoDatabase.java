package com.example.todoapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();

    private static volatile TodoDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TodoDatabase getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (TodoDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                TodoDatabase.class, "Todo_database")
                                .addCallback(TodoDatabaseCallback)
                                .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback TodoDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                TodoDao dao = INSTANCE.todoDao();
                dao.deleteAll();

                Todo todo = new Todo("Hello", 0);
                dao.insert(todo);
                todo = new Todo("World", 0);
                dao.insert(todo);
            });
        }
    };
}
