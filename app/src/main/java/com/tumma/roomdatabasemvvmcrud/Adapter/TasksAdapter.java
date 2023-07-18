package com.tumma.roomdatabasemvvmcrud.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.tumma.roomdatabasemvvmcrud.Interface.OnClickListener_;
import com.tumma.roomdatabasemvvmcrud.Model.Task;
import com.tumma.roomdatabasemvvmcrud.R;
import com.tumma.roomdatabasemvvmcrud.View.AddTaskActivity;
import com.tumma.roomdatabasemvvmcrud.View.MainActivity;
import com.tumma.roomdatabasemvvmcrud.ViewModel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> implements Filterable {

    OnClickListener_ onClickListener;
    private Context mCtx;
    private List<Task> taskList;
    List<Task> detailsFiltered;
    int pos = -1;
    int selectedItem = -1;
    PopupWindow popupWindow;

    public TasksAdapter(Context mCtx, List<Task> taskList, OnClickListener_ onClickListener) {
        this.mCtx = mCtx;
        this.taskList = taskList;
        detailsFiltered = new ArrayList<>(taskList);
        this.onClickListener=onClickListener;

    }

    public void settasks(List<Task> tasks, Context mCtx) {
        this.taskList = tasks;
        this.mCtx = mCtx;
        notifyDataSetChanged();
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TasksViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Task t = taskList.get(position);
        holder.textViewTask.setText(t.getTask());
        holder.textViewTime.setText(t.getStarttime() + " to " + t.getEndtime());

        Log.e("onBindViewHolder: ",taskList.get(position).getDate() );

        pos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = position;
                notifyDataSetChanged();
            }
        });

        holder.imgUpdatedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = position;
                notifyDataSetChanged();
                try {
                    popUpmenu(holder.imgUpdatedelete, position);
                } catch (Exception e) {
                    Toast.makeText(mCtx, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
        return taskList.size();
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    class TasksViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTask, textViewTime;
        CardView cardview;
        ImageView taskicon, imgUpdatedelete;

        public TasksViewHolder(View itemView) {
            super(itemView);

            taskicon = itemView.findViewById(R.id.taskicon);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            imgUpdatedelete = itemView.findViewById(R.id.imgUpdatedelete);
            cardview = itemView.findViewById(R.id.cardview);
        }

    }

    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Task> filterlist = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterlist.addAll(detailsFiltered);


            } else {
                String fillpatern = constraint.toString().toLowerCase().trim();
                for (Task item : detailsFiltered) {
                    if (item.getTask().toLowerCase().contains(fillpatern)) {
                        filterlist.add(item);
                    }
                }
            }


            FilterResults result = new FilterResults();
            result.values = filterlist;


            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            taskList.clear();
            taskList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public Task getTaskAt(int position) {
        Task Task = taskList.get(position);
        Task.setId(taskList.get(position).getId());
        return Task;
    }

    void popUpmenu(View view, final int pos) {

        PopupMenu popup = new PopupMenu(mCtx, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().toString().contains("Update")) {

                    Task task = taskList.get(pos);
                    Intent intent = new Intent(mCtx, AddTaskActivity.class);
                    intent.putExtra("task", task);
                    intent.putExtra("flag", "1");
                    mCtx.startActivity(intent);

                } else {
                    onClickListener.delete(pos);
                }

                Toast.makeText(mCtx, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popup.show();//showing popup menu
    }
//    public class PopUpClass {
//        public void showPopupWindow(final View view) {
//
//            //Create a View object yourself through inflater
//            LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
//            View popupView = inflater.inflate(R.layout.pop_up_layout, null);
//
//            //Specify the length and width through constants
//            int width = LinearLayout.LayoutParams.MATCH_PARENT;
//            int height = LinearLayout.LayoutParams.MATCH_PARENT;
//
//            //Make Inactive Items Outside Of PopupWindow
//            boolean focusable = true;
//
//            //Create a window with our parameters
//            popupWindow = new PopupWindow(popupView, width, height, focusable);
//
//            //Set the location of the window on the screen
//            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//
//            //Initialize the elements of our window, install the handler
//
//            TextView test2 = popupView.findViewById(R.id.titleText);
//            test2.setText(R.string.textTitle);
//
//            Button buttonEdit = popupView.findViewById(R.id.messageButton);
//            buttonEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //As an example, display the message
//                    Toast.makeText(view.getContext(), "Wow, popup action button", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//
//            //Handler for clicking on the inactive zone of the window
//
//            popupView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//
//                    //Close the window when clicked
//                    popupWindow.dismiss();
//                    return true;
//                }
//            });
//        }
//    }

}