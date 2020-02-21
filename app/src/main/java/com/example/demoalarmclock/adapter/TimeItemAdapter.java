package com.example.demoalarmclock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoalarmclock.R;
import com.example.demoalarmclock.listener.OnItemTimeClick;
import com.example.demoalarmclock.model.Timer;

import java.util.List;

public class TimeItemAdapter extends RecyclerView.Adapter<TimeItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Timer> timerList;
    private OnItemTimeClick onItemTimeClick;

    public TimeItemAdapter(Context mContext, List<Timer> timerList, OnItemTimeClick onItemTimeClick) {
        this.mContext = mContext;
        this.timerList = timerList;
        this.onItemTimeClick = onItemTimeClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_alarm_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Timer timer = timerList.get(position);
        holder.onBind(timer);
    }

    @Override
    public int getItemCount() {
        return timerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTimer;
        private ImageView imgFix, imgDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimer = itemView.findViewById(R.id.tv_time_item);
            imgDel = itemView.findViewById(R.id.img_del);
            imgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemTimeClick.onDelCLick(getAdapterPosition());
                }
            });
            imgFix = itemView.findViewById(R.id.img_fix);
            imgFix.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemTimeClick.onFixCLick(getAdapterPosition());
                }
            });
        }
        void onBind(Timer timer){
            tvTimer.setText(timer.getTime());
        }
    }
}
