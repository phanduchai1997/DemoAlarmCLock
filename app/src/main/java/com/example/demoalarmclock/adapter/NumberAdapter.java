package com.example.demoalarmclock.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoalarmclock.R;
import com.example.demoalarmclock.listener.Onclick;
import com.example.demoalarmclock.model.Number;

import java.util.List;

public class NumberAdapter extends RecyclerView.Adapter {
    private List<Number> numberList;
    private Context mContext;
    private Onclick onclick;

    public NumberAdapter(List<Number> numberList, Context mContext, Onclick onclick) {
        this.numberList = numberList;
        this.mContext = mContext;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_number, parent, false);
            return new ViewHolder(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_button_back, parent, false);
        return new ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (numberList.get(position).getType()==1) {
            Number number = numberList.get(position);
            ((ViewHolder) holder).button.setText(number.getNumber());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (numberList.get(position).getType()==1) {
            return 1;
        } else return -1;

    }

    @Override
    public int getItemCount() {
        return numberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btn_number);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclick.onNumberAdd(getAdapterPosition());
                }
            });
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.btn_number_back);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclick.onNumberBack(getAdapterPosition());
                }
            });
        }
    }
}
