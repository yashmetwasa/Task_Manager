package com.example.taskmanager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.taskmanager.adapter.RVAdapter;
import com.example.taskmanager.model.DaoSession;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView tasksRV;
    private TaskDao taskDao;

    FloatingActionButton add;


    @Override
    public void onResume() {
        super.onResume();

        reloadRV();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        add = findViewById(R.id.btnAdd);
        add.setOnClickListener(this);

        tasksRV = findViewById(R.id.tasks_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        tasksRV.setLayoutManager(linearLayoutManager);
        tasksRV.setHasFixedSize(true);
        //tasksRV.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void reloadRV() {
        DaoSession daoSession = ((TaskManager) getApplication()).db();
        taskDao = daoSession.getTaskDao();
        List<Task> tasks = taskDao.loadAll();

        RVAdapter adapter = new RVAdapter(taskDao, this, taskDao.loadAll());
        if (tasksRV.getAdapter() == null) {
            tasksRV.setAdapter(adapter);
        } else {
            tasksRV.swapAdapter(adapter, false);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }


}