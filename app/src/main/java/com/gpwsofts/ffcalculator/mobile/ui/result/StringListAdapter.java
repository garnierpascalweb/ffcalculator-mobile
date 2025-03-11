package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

/**
 * Adapter pour une liste déroulante de String (lieu ) (item objet Integer, rendu IntegerViewHolder)
 * @since 1.0.0
 */
public class StringListAdapter extends ListAdapter<String, StringViewHolder>  {

    protected StringListAdapter(@NonNull DiffUtil.ItemCallback<String> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return StringViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
        String current = getItem(position);
        holder.bind(current);
    }

    public static class StringDiff extends DiffUtil.ItemCallback<String> {

        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    }
}
