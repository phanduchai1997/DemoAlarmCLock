package com.example.demoalarmclock;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoalarmclock.adapter.TimeItemAdapter;
import com.example.demoalarmclock.database.TimerDatabase;
import com.example.demoalarmclock.listener.OnItemTimeClick;
import com.example.demoalarmclock.model.Timer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListAlarmActivity extends AppCompatActivity implements OnItemTimeClick {
    private List<Timer> timerList;
    private RecyclerView recyclerView;
    private TimeItemAdapter itemAdapter;
    TimerDatabase database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_alarm);
        database = new TimerDatabase(this,"Timer.sqlite",null,1);
        timerList = new ArrayList<>();
        addTimer();
        itemAdapter = new TimeItemAdapter(this, timerList,this);
        recyclerView = findViewById(R.id.rv_list_timer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);


    }

    private void addTimer() {
        Cursor timer = database.getData("SELECT * FROM time");
        while (timer.moveToNext()){
            timerList.add(new Timer(timer.getInt(1)+":"+(timer.getInt(2))+"("+timer.getString(3)+")"));
        }
    }

    @Override
    public void onFixCLick(int posision) {
    }

    @Override
    public void onDelCLick(int posision) {
        timerList.remove(posision);
        itemAdapter.notifyDataSetChanged();
    }
}
