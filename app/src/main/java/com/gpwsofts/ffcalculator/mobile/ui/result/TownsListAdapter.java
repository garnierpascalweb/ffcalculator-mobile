package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gpwsofts.ffcalculator.mobile.R;

import java.util.List;

public class TownsListAdapter extends ListAdapter<String, TownsViewHolder> {
    protected TownsListAdapter(@NonNull DiffUtil.ItemCallback<String> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public TownsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TownsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TownsViewHolder holder, int position) {
        String current = getItem(position);
        holder.bind(current);
    }

    public static class TownsDiff extends DiffUtil.ItemCallback<String> {

        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            boolean areTheSameContents = (oldItem.equals(newItem));
            return areTheSameContents;
        }
    }
}
