package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.gpwsofts.ffcalculator.mobile.dao.Result;

public class ResultListAdapter extends ListAdapter<Result, ResultViewHolder> {
    public ResultListAdapter(@NonNull DiffUtil.ItemCallback<Result> diffCallback) {
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
        holder.bind(current.getLogo(), current.getLogoColor(), current.getPlace(), current.getLibelle(), current.getPos(), current.getPrts(), current.getPts());
    }
//TODO 1.0.0 bizarre methode protegee mais
    public Result getItemFromAdapter(int position){
        return getItem(position);
    }


    public static class ResultDiff extends DiffUtil.ItemCallback<Result> {

        @Override
        public boolean areItemsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            boolean areTheSameContents = (oldItem.idClasse.equals(newItem.idClasse)) &&
                    (oldItem.place.equals(newItem.place)) &&
                    (oldItem.logo.equals(newItem.logo)) &&
                    (oldItem.logoColor == newItem.logoColor) &&
                    (oldItem.pos == newItem.pos) &&
                    (oldItem.prts == newItem.prts) &&
                    (oldItem.pts == newItem.pts) &&
                    (oldItem.libelle.equals(newItem.libelle));
            return areTheSameContents;
        }
    }
}
