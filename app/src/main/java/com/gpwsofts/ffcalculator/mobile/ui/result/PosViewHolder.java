package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.model.Grid;

/**
 * Reprend les elements qui ont été définis dans notre custom Layout : simple_spinner_item
 */
public class PosViewHolder extends RecyclerView.ViewHolder {
    /**
     * le textView de notre item
     */
    private final TextView spinnerItemTextView;
    public PosViewHolder(@NonNull View itemView) {
        super(itemView);
        spinnerItemTextView = (TextView) itemView;
    }
    /**
     * Chargement depuis le layour que nous avons défini : simple_spinner_item
     * @param parent
     * @return
     */
    static PosViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_spinner_item, parent, false);
        return new PosViewHolder(view);
    }
    public void bind(Integer current) {
        spinnerItemTextView.setText(String.valueOf(current));
    }
}
