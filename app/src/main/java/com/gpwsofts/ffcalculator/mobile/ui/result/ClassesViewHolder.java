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
public class ClassesViewHolder extends RecyclerView.ViewHolder {
    /**
     * Le Text View de notre Item
     */
    private final TextView spinnerItemTextView;

    public ClassesViewHolder(@NonNull View itemView) {
        super(itemView);
        spinnerItemTextView = (TextView) itemView;
    }

    /**
     * Chargement depuis le layour que nous avons défini : simple_spinner_item
     * @param parent
     * @return
     */
    static ClassesViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_spinner_item, parent, false);
        return new ClassesViewHolder(view);
    }

    /**
     * Permet d'associer l'instance de l'item en cours (de type Grid) a l'objet graphique défini dans notre custom layout
     * @param current
     */
    public void bind(Grid current) {
        spinnerItemTextView.setText(current.getSpinnerItemValue());
    }
}
