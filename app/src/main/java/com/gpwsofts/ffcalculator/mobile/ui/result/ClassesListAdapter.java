package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.gpwsofts.ffcalculator.mobile.model.grid.Grid;
import com.gpwsofts.ffcalculator.mobile.model.grid.IGrid;

/**
 * @since 1.0.0
 * Adapter pour la liste déroulante des classes (item objet Grid, rendu ClassesViewHolder)
 */
public class ClassesListAdapter extends ListAdapter<IGrid, ClassesViewHolder> {
    protected ClassesListAdapter(@NonNull DiffUtil.ItemCallback<IGrid> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ClassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ClassesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassesViewHolder holder, int position) {
        IGrid current = getItem(position);
        holder.bind(current);
    }

    public static class ClassesDiff extends DiffUtil.ItemCallback<IGrid> {

        @Override
        public boolean areItemsTheSame(@NonNull IGrid oldItem, @NonNull IGrid newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull IGrid oldItem, @NonNull IGrid newItem) {
            return (oldItem.getCode().equals(newItem.getCode()));
        }
    }
}
