package com.tumma.roomdatabasemvvmcrud.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tumma.roomdatabasemvvmcrud.Interface.CategoryInterface;
import com.tumma.roomdatabasemvvmcrud.R;

import java.util.ArrayList;

public class RecyclerViewCategoryTaskAdapter extends RecyclerView.Adapter<RecyclerViewCategoryTaskAdapter.RecyclerViewHolder> {
    ArrayList<String> arrayList = new ArrayList<>();
    private Context mcontext;
    int selectedItempos = -1;
    CategoryInterface categoryInterface;

    public RecyclerViewCategoryTaskAdapter(ArrayList<String> recyclerDataArrayList, Context mcontext, CategoryInterface categoryInterface) {
        this.arrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
        this.categoryInterface=categoryInterface;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.descrlayout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // Set the data to textview and imageview.
        holder.txt_descr.setText(arrayList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItempos = position;
                categoryInterface.CategoryData(arrayList.get(position));
                notifyDataSetChanged();

            }
        });

        if (selectedItempos == position) {
            holder.txt_descr.setBackgroundResource(R.drawable.chiproundselected);
            holder.txt_descr.setTextColor(mcontext.getResources().getColor(R.color.pinkdark));
        }
        else {
            holder.txt_descr.setBackgroundResource(R.drawable.chipround);
            holder.txt_descr.setTextColor(mcontext.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return arrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_descr;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_descr = itemView.findViewById(R.id.chipCpp);
        }
    }
}
