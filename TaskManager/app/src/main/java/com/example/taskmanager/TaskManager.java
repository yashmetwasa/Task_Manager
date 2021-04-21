package com.example.taskmanager;

import android.app.Application;

import com.example.taskmanager.model.DaoMaster;
import com.example.taskmanager.model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class TaskManager extends Application {

    private DaoSession session;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"tasks.db");
        Database db = helper.getWritableDb();

        DaoMaster master = new DaoMaster(db);
        session = master.newSession();
    }
    
    public DaoSession db(){
        return this.session;
    }

}
