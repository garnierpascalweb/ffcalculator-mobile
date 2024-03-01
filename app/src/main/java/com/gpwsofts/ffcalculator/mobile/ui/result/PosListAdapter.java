package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

/**
 * Adapter pour la liste déroulante des positions (item objet Integer, rendu PosViewHolder)
 * @since 1.0.0
 */
public class PosListAdapter extends ListAdapter<Integer, PosViewHolder> {
    protected PosListAdapter(@NonNull DiffUtil.ItemCallback<Integer> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PosViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PosViewHolder holder, int position) {
        int current = getItem(position);
        holder.bind(current);
    }

    public static class PosDiff extends DiffUtil.ItemCallback<Integer> {

        @Override
        public boolean areItemsTheSame(@NonNull Integer oldItem, @NonNull Integer newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Integer oldItem, @NonNull Integer newItem) {
            return oldItem.equals(newItem);
        }
    }
}
