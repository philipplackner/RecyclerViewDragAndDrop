package com.androiddevs.recyclerviewdraganddrop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView rvItems = findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        final ItemTouchHelper helper;

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {

                RecyclerViewAdapter adapter = (RecyclerViewAdapter) rvItems.getAdapter();
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                adapter.moveRecyclerViewItem(from, to);
                adapter.notifyItemMoved(from, to);

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);

                if(actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setBackgroundColor(getColor(R.color.lightergrey2));
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(getColor(R.color.darkergrey2));
            }
        };

        helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvItems);

        rvItems.setAdapter(new RecyclerViewAdapter(new OnDragStartListener() {
            @Override
            public void onDragStart(RecyclerViewAdapter.RecyclerViewHolder holder) {
                helper.startDrag(holder);
            }
        }, new ArrayList<>(Arrays.asList("AndroidDevs", "Instagram", "YouTube", "Tinder", "Facebook",
                "WhatsApp", "Discord", "Telegram"))));
    }
}
