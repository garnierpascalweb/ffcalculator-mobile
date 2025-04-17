package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.gpwsofts.ffcalculator.mobile.model.grid.Grid;

/**
 * @since 1.0.0
 * Adapter pour la liste déroulante des classes (item objet Grid, rendu ClassesViewHolder)
 */
public class ClassesListAdapter extends ListAdapter<Grid, ClassesViewHolder> {
    protected ClassesListAdapter(@NonNull DiffUtil.ItemCallback<Grid> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ClassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ClassesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassesViewHolder holder, int position) {
        Grid current = getItem(position);
        holder.bind(current);
    }

    public static class ClassesDiff extends DiffUtil.ItemCallback<Grid> {

        @Override
        public boolean areItemsTheSame(@NonNull Grid oldItem, @NonNull Grid newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Grid oldItem, @NonNull Grid newItem) {
            return (oldItem.getCode().equals(newItem.getCode()));
        }
    }
}
