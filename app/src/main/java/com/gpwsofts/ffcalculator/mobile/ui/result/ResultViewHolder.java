package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.view.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gpwsofts.ffcalculator.mobile.R;

/**
 * ViewHolder d'un resultat, a l'image de d'un item de la liste
 * @since 1.0.0
 */
public class ResultViewHolder extends RecyclerView.ViewHolder {
    private final TextView logoTextView;
    // @+id/idTVResultPlace
    private final TextView placeTextView;
    // +id/idTVLibelle
    private final TextView libelleTextView;
    // @+id/idTVResultPosPrts"
    private final TextView resultPosPrtsTextView;
    // @+id/idTVResultPts
    private final TextView resultPtsView;

    public ResultViewHolder(View itemView) {
        super(itemView);
        logoTextView = itemView.findViewById(R.id.idTVResultLogo);
        placeTextView = itemView.findViewById(R.id.idTVResultPlace);
        libelleTextView = itemView.findViewById(R.id.idTVLibelle);
        resultPosPrtsTextView = itemView.findViewById(R.id.idTVResultPosPrts);
        resultPtsView = itemView.findViewById(R.id.idTVResultPts);
    }
    public void bind(String logo, String place, String libelle, int pos, int prts, double pts){
        logoTextView.setText(logo);
        placeTextView.setText(place);
        libelleTextView.setText(libelle);
        //TODO 1.0.0 Faire les StringBuilder de merde
        resultPosPrtsTextView.setText(prts);
        resultPtsView.setText(String.valueOf(pts));
    }
    static ResultViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_result_layout, parent, false);
        return new ResultViewHolder(view);
    }
}