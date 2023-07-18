package com.tumma.roomdatabasemvvmcrud.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tumma.roomdatabasemvvmcrud.Interface.CategoryInterface;
import com.tumma.roomdatabasemvvmcrud.Model.Task;
import com.tumma.roomdatabasemvvmcrud.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<Task> tasks = new ArrayList<>();
    private OnTaskClickListner onTaskClickListner;
    private CategoryInterface categoryInterface;
    Context mCtx;
    int selectedItem=-1;
    public void settasks(List<Task> tasks, Context mCtx) {
        this.tasks = tasks;
        this.mCtx = mCtx;
        notifyDataSetChanged();
    }

    public RecyclerViewAdapter(ArrayList<Task> arrayList, Context mcontext, CategoryInterface categoryInterface) {
        this.tasks = arrayList;
        this.mCtx = mCtx;
        this.categoryInterface=categoryInterface;
    }

    public void setItemOnClick(OnTaskClickListner onTaskClickListner){
        this.onTaskClickListner = onTaskClickListner;
    }

    public Task getTaskAt(int position)
    {
        Task Task = tasks.get(position);
        Task.setId(tasks.get(position).getId());
        return Task;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,onTaskClickListner);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Task t = tasks.get(position);
        holder.textViewTask.setText(t.getTask());
        holder.textViewTime.setText(t.getStarttime()+ " to "+ t.getEndtime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = position;
                notifyDataSetChanged();
            }
        });

        if (selectedItem == position) {
            holder.cardview.setCardElevation(20);
            holder.cardview.setCardBackgroundColor(mCtx.getResources().getColor(R.color.white));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.taskicon.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.calender, mCtx.getTheme()));
            } else {
                holder.taskicon.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.calender, mCtx.getTheme()));
            }

        } else {
            holder.cardview.setCardElevation(0);
            holder.cardview.setCardBackgroundColor(mCtx.getResources().getColor(R.color.bluelight));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.taskicon.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.calender_, mCtx.getTheme()));
            } else {
                holder.taskicon.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.calender_, mCtx.getTheme()));
            }

        }

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewTask, textViewTime;
        CardView cardview;
        ImageView taskicon,imgUpdatedelete;
        private OnTaskClickListner mListener;

        public RecyclerViewHolder(@NonNull View itemView, OnTaskClickListner onTaskClickListner) {
            super(itemView);
            this.mListener = onTaskClickListner;
            itemView.setOnClickListener(this);

            taskicon = itemView.findViewById(R.id.taskicon);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            imgUpdatedelete = itemView.findViewById(R.id.imgUpdatedelete);
            cardview = itemView.findViewById(R.id.cardview);
            imgUpdatedelete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Task currentTask = tasks.get(position);
            Task Task = new Task(currentTask.getId(),currentTask.getTask(),currentTask.getDate(),currentTask.getStarttime(),currentTask.getEndtime(),currentTask.getDesc(), currentTask.getCategory());
            Task.setId(currentTask.getId());
            mListener.onTaskClick(Task);
        }
    }

    public interface OnTaskClickListner{
        void onTaskClick(Task Task);
    }
}
