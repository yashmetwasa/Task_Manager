package com.example.taskmanager.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.MainActivity;
import com.example.taskmanager.R;
import com.example.taskmanager.TaskManager;
import com.example.taskmanager.model.DaoSession;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskDao;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private final List<Task> tasks;
    private Context context;
    private TaskDao taskDao;

    public RVAdapter(TaskDao taskDao1, Context context, List<Task> tasks) {
        this.taskDao = taskDao1;
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basic_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Task modelClass = tasks.get(position);
        holder.title_tv.setText(tasks.get(position).getTitle());
        holder.desc_tv.setText("" + tasks.get(position).getPriority());
        holder.title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteDialog(modelClass);
            }
        });
        holder.desc_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteDialog(modelClass);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title_tv;
        TextView desc_tv;

        public ViewHolder(@NonNull View v) {
            super(v);
            v.setOnClickListener(this);
            this.title_tv = v.findViewById(R.id.title_tv);
            this.desc_tv = v.findViewById(R.id.desc_tv);

        }

        @Override
        public void onClick(View v) {
            //ToDo: Remove a task by clicking on it
        }
    }

    private void DeleteDialog(Task modelClass) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.list_dialog, null);
        TextView name = subView.findViewById(R.id.name);
        TextView priority = subView.findViewById(R.id.priority);

        if (modelClass != null) {
            name.setText("Title: " + modelClass.getTitle());
            priority.setText("Priority: " + modelClass.getPriority());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure?");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Accomplished", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                taskDao.delete(modelClass);
                Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
                ((MainActivity) context).reloadRV();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(context, "item cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
