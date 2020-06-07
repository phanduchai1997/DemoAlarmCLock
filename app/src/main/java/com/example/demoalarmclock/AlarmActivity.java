package com.example.demoalarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoalarmclock.adapter.DateAdapter;
import com.example.demoalarmclock.adapter.NumberAdapter;
import com.example.demoalarmclock.broadcast.AlarmBroadcast;
import com.example.demoalarmclock.database.TimerDatabase;
import com.example.demoalarmclock.listener.Onclick;
import com.example.demoalarmclock.model.Date;
import com.example.demoalarmclock.model.Number;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmActivity extends AppCompatActivity implements Onclick, View.OnClickListener {
    private RecyclerView rvDate, rvNumber;
    private DateAdapter dateAdapter;
    private NumberAdapter numberAdapter;
    private List<Date> list;
    private List<Number> listNumbers;
    private ImageView imgBack;
    private Button btnCancel, btnSave, btnAmPm;
    private List<TextView> textViewList;
    private int count = 3;
    private Calendar calendar;
    private AlarmManager alarmManager;
    public static final String START_ALARM = "START_ALARM";
    private TextView tvTime1, tvTime2, tvTime3, tvTime4;
    private int hour = 0;
    private int minute = 0;
    private String amOrPm ="";
    private AlarmBroadcast alarmBroadcast;
    private List<Integer> listDayOfWeek = new ArrayList<>();
    private TimerDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        IntentFilter intentFilter = new IntentFilter(START_ALARM);
        alarmBroadcast = new AlarmBroadcast();
        registerReceiver(alarmBroadcast,intentFilter);
        database = new TimerDatabase(this,"Timer.sqlite",null,1);
        database.query("CREATE TABLE IF NOT EXISTS time(id INTEGER PRIMARY KEY AUTOINCREMENT, hour INTEGER, minute INTEGER, Am_Pm VARCHAR(200))");
        initView();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(alarmBroadcast);
        super.onDestroy();
    }

    private void initView() {
        list = new ArrayList<>();
        addList();
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        imgBack = findViewById(R.id.img_back);
        btnCancel = findViewById(R.id.btn_cancel);
        textViewList = new ArrayList<>();
        tvTime1 = findViewById(R.id.tv_time1);
        tvTime2 = findViewById(R.id.tv_time2);
        tvTime3 = findViewById(R.id.tv_time3);
        tvTime4 = findViewById(R.id.tv_time4);
        textViewList.add(tvTime1);
        textViewList.add(tvTime2);
        textViewList.add(tvTime3);
        textViewList.add(tvTime4);
        btnAmPm = findViewById(R.id.button_am_pm);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAmPm.setOnClickListener(this);
        dateAdapter = new DateAdapter(list, this, this);
        rvDate = findViewById(R.id.rv_date);
        rvDate.setLayoutManager(new GridLayoutManager(this, 7));
        rvDate.setAdapter(dateAdapter);
        rvNumber = findViewById(R.id.rv_number);
        listNumbers = new ArrayList<>();
        addListNumber();
        numberAdapter = new NumberAdapter(listNumbers, this, this);
        rvNumber.setLayoutManager(new GridLayoutManager(this, 3));
        rvNumber.setAdapter(numberAdapter);

    }

    private void addListNumber() {
        listNumbers.add(new Number("1", 1));
        listNumbers.add(new Number("2", 1));
        listNumbers.add(new Number("3", 1));
        listNumbers.add(new Number("4", 1));
        listNumbers.add(new Number("5", 1));
        listNumbers.add(new Number("6", 1));
        listNumbers.add(new Number("7", 1));
        listNumbers.add(new Number("8", 1));
        listNumbers.add(new Number("9", 1));
        listNumbers.add(new Number("00", 1));
        listNumbers.add(new Number("0", 1));
        listNumbers.add(new Number("0", -1));
    }

    private void addList() {
        list.add(new Date("Mo"));
        list.add(new Date("Tue"));
        list.add(new Date("Wed"));
        list.add(new Date("Thur"));
        list.add(new Date("Fri"));
        list.add(new Date("Sat"));
        list.add(new Date("Sun"));
    }

    @Override
    public void onDateClick(int posision) {
        list.get(posision).setType(-1);
        dateAdapter.notifyDataSetChanged();
        if (list.get(posision).getDate().equals("MO")) {
            listDayOfWeek.add(1);
        } else if (list.get(posision).getDate().equals("TUE")) {
            listDayOfWeek.add(2);
        } else if (list.get(posision).getDate().equals("WED")) {
            listDayOfWeek.add(3);
        } else if (list.get(posision).getDate().equals("THUR")) {
            listDayOfWeek.add(4);
        } else if (list.get(posision).getDate().equals("FRI")) {
            listDayOfWeek.add(5);
        } else if (list.get(posision).getDate().equals("SAT")) {
            listDayOfWeek.add(6);
        } else listDayOfWeek.add(7);

    }

    @Override
    public void onStopDateClick(int posision) {
        list.get(posision).setType(1);
        dateAdapter.notifyDataSetChanged();
        if (list.get(posision).getDate().equals("MO")) {
            listDayOfWeek.remove(new Integer(1));
        } else if (list.get(posision).getDate().equals("TUE")) {
            listDayOfWeek.remove(new Integer(2));
        } else if (list.get(posision).getDate().equals("WED")) {
            listDayOfWeek.remove(new Integer(3));
        } else if (list.get(posision).getDate().equals("THUR")) {
            listDayOfWeek.remove(new Integer(4));
        } else if (list.get(posision).getDate().equals("FRI")) {
            listDayOfWeek.remove(new Integer(5));
        } else if (list.get(posision).getDate().equals("SAT")) {
            listDayOfWeek.remove(new Integer(6));
        } else listDayOfWeek.remove(new Integer(7));
    }

    @Override
    public void onNumberAdd(int posision) {
        String number = listNumbers.get(posision).getNumber();

        textViewList.get(count).setText(number);
        if (number.equals("00")) {
            if (count <= 0)
                count = 1;
            textViewList.get(count).setText("0");
            count--;
            textViewList.get(count).setText("0");

        }
        if (!textViewList.get(3).getText().equals("_") && !textViewList.get(2).getText().equals("_") &&
                !textViewList.get(1).getText().equals("_") && !textViewList.get(0).getText().equals("_")) {
            hour = Integer.valueOf(textViewList.get(3).getText().toString() + textViewList.get(2).getText().toString());
            minute = Integer.valueOf(textViewList.get(1).getText().toString() + textViewList.get(0).getText().toString());

            String h = "0";
            String m = "";
            if (hour > 12) {
                hour = hour % 12;
            }
            if (minute > 60)
                m = "60";
            else if (minute < 10) {
                m = "0";
            }
            m = m + minute;
            h = h + hour;

            tvTime4.setText(h.charAt(0) + "");
            tvTime3.setText(h.charAt(1) + "");
            tvTime2.setText(m.charAt(0) + "");
            tvTime1.setText(m.charAt(1) + "");
        }

        count--;
        if (count < 0)
            count = 0;
    }

    @Override
    public void onNumberBack(int posision) {


        if (count == 0)
            tvTime1.setText("_");
        if (count == 1)
            tvTime2.setText("_");
        if (count == 2)
            tvTime3.setText("_");
        if (count == 3)
            tvTime4.setText("_");
        count++;
        if (count > 3)
            count = 3;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_am_pm:
                if (btnAmPm.getText().equals("AM")) {
                    btnAmPm.setText("PM");
                    amOrPm = "PM";
                } else {
                    btnAmPm.setText("AM");
                    amOrPm = "AM";
                }
                break;
            case R.id.img_back:
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_save:
                saveTimer();
                break;
        }
    }

    private void saveTimer() {
        database.query("INSERT INTO time VALUES(null, "+hour+", "+minute+", '"+amOrPm+"')");
        amOrPm= btnAmPm.getText().toString();
        Intent intent = new Intent(START_ALARM);
        if (amOrPm.equals("AM")) {
            calendar.set(Calendar.HOUR_OF_DAY, hour+12);
        } else calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        Intent intent1 = new Intent(AlarmActivity.this,ListAlarmActivity.class);
        startActivity(intent1);
    }
}

