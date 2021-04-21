package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.model.DaoSession;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskDao;

public class TaskActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {


    private static final String priorityBaseStr = "Choose priority: %d";

    private EditText titleET;
    private TextView priorityTV;
    private SeekBar prioritySB;
    private TaskDao taskDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        DaoSession daoSession = ((TaskManager) getApplication()).db();
        taskDao = daoSession.getTaskDao();
        findViewById(R.id.save_btn).setOnClickListener(this);

        titleET = findViewById(R.id.title_et);
        priorityTV = findViewById(R.id.priority_tv);
        prioritySB = findViewById(R.id.priority_sb);

        priorityTV.setText(String.format(priorityBaseStr, 1));
        prioritySB.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        String title = titleET.getText().toString();
        Integer priority = prioritySB.getProgress();

        Task task = new Task();
        task.setTitle(title);
        task.setPriority(priority);

        try {
            taskDao.insert(task);
        } catch (SQLiteConstraintException e) {
            Toast.makeText(TaskActivity.this, "Title exists!", Toast.LENGTH_SHORT).show();
            titleET.setText("");
        }
        finish();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        priorityTV.setText(String.format(priorityBaseStr, progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}