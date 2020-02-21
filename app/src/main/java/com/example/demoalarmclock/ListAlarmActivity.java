package com.example.demoalarmclock;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoalarmclock.adapter.TimeItemAdapter;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_alarm);
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
        File file = new File("data/data/com.example.demoalarmclock/time.txt");
        String time = "";
        if(!file.exists()){
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int len;
            byte buff[] = new byte[1024];
            while ((len = inputStream.read(buff))>0){
                time += new String(buff,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String listTime[] = time.split("\n");
        for (int i=0;i<listTime.length;i++){
            String listInf[] = listTime[i].split(":");
            String time1=listInf[0]+":"+listInf[1]+"("+listInf[2]+")";
                timerList.add(new Timer(time1));
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
