package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

/**
 * Adapter pour une liste déroulante de Integer (positions, partants) (item objet Integer, rendu IntegerViewHolder)
 *
 * @since 1.0.0
 */
public class IntegerListAdapter extends ListAdapter<Integer, IntegerViewHolder> {
    protected IntegerListAdapter(@NonNull DiffUtil.ItemCallback<Integer> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public IntegerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return IntegerViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull IntegerViewHolder holder, int position) {
        int current = getItem(position);
        holder.bind(current);
    }

    public static class IntDiff extends DiffUtil.ItemCallback<Integer> {

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
