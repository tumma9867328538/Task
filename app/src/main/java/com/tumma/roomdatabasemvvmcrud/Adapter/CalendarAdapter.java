package com.tumma.roomdatabasemvvmcrud.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.tumma.roomdatabasemvvmcrud.Interface.CalenderPickDate;
import com.tumma.roomdatabasemvvmcrud.Model.MyCalendar;
import com.tumma.roomdatabasemvvmcrud.R;

import java.util.List;


public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {

    private List<MyCalendar> mCalendar;
    private int recyclecount=0;
    int selectedItem=-1;
    Context mctx;

    CalenderPickDate calenderPickDate;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tb_day, tb_date;

        public MyViewHolder(View view) {
            super(view);
            tb_day = (TextView) view.findViewById(R.id.day_1);
            tb_date = (TextView) view.findViewById(R.id.date_1);
        }
    }

    public CalendarAdapter(List<MyCalendar> mCalendar, Context context, CalenderPickDate calenderPickDate) {
        this.mCalendar = mCalendar;
        this.mctx=context;
        this.calenderPickDate=calenderPickDate;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_calender_day, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final MyCalendar calendar = mCalendar.get(position);

        holder.tb_day.setText(calendar.getDay()+"");

        holder.tb_date.setText(calendar.getDate()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), calendar.getDate(), Toast.LENGTH_SHORT).show();
//                Log.e("onClick: ", calendar.getDate());
                calenderPickDate.CalenderPickDate(calendar.getDate());
                selectedItem = position;
                notifyDataSetChanged();
            }
        });

        if (selectedItem==position){
            holder.itemView.setBackgroundDrawable(mctx.getResources().getDrawable(R.drawable.calenderselector_));
            holder.tb_date.setTextColor(mctx.getResources().getColor(R.color.black));
            holder.tb_day.setTextColor(mctx.getResources().getColor(R.color.graydark
            ));

        }else {
            holder.itemView.setBackgroundDrawable(mctx.getResources().getDrawable(R.drawable.calenderselectorr));
            holder.tb_date.setTextColor(mctx.getResources().getColor(R.color.white));
            holder.tb_day.setTextColor(mctx.getResources().getColor(R.color.silver));
        }


        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCalendar.size();
    }

    @Override
    public void onViewRecycled (MyViewHolder holder){

        recyclecount++;

    }




}
