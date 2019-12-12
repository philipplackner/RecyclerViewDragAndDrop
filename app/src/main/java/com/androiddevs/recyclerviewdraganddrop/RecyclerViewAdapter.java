package com.androiddevs.recyclerviewdraganddrop;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    ArrayList<String> items;
    OnDragStartListener listener;

    public RecyclerViewAdapter(OnDragStartListener listener, ArrayList<String> items) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
        holder.tvTitle.setText(items.get(position));
        holder.ivReorder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    listener.onDragStart(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void moveRecyclerViewItem(int from, int to) {
        String stringToMove = items.get(from);
        items.remove(from);

        if(to < from) {
            items.add(to, stringToMove);
        } else {
            items.add(to - 1, stringToMove);
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivReorder;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivReorder = itemView.findViewById(R.id.ivReorder);
        }
    }
}
