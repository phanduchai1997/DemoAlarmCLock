package com.example.demoalarmclock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoalarmclock.R;
import com.example.demoalarmclock.listener.Onclick;
import com.example.demoalarmclock.model.Date;
import com.example.demoalarmclock.model.Number;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter{
    private List<Date> dateList;
    private Context mContext;
    private Onclick onclick;

    public DateAdapter(List<Date> dateList, Context mContext, Onclick onclick) {
        this.dateList = dateList;
        this.mContext = mContext;
        this.onclick = onclick;
    }

    @Override
    public int getItemViewType(int position) {
        if(dateList.get(position).getType()==1){
            return 1;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1){
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_date,parent,false);
            return new ViewHolder(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_date1,parent,false);
        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Date date = dateList.get(position);
        if(dateList.get(position).getType()==1){
            ((ViewHolder)holder).button.setText(date.getDate());
        }
        else ((ViewHolder1)holder).button.setText(date.getDate());
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btn_date);
           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onclick.onDateClick(getAdapterPosition());
               }
           });
        }
    }
    public class ViewHolder1 extends RecyclerView.ViewHolder{
        private Button button;
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btn_date1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclick.onStopDateClick(getAdapterPosition());
                }
            });
        }
    }
}
