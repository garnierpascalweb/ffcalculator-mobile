package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.gpwsofts.ffcalculator.mobile.dao.Result;

public class ResultListAdapter extends ListAdapter<Result, ResultViewHolder> {
    protected ResultListAdapter(@NonNull DiffUtil.ItemCallback<Result> diffCallback) {
        super(diffCallback);
    }
    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ResultViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        Result current = getItem(position);
        holder.bind(current.getLogo(), current.getPlace(), current.getLibelle(), current.getPos(), current.getPrts(), current.getPts());
    }

    static class ResultDiff extends DiffUtil.ItemCallback<Result>{

        @Override
        public boolean areItemsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            return false;
        }
    }
}
