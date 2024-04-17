package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gpwsofts.ffcalculator.mobile.R;

/**
 * ViewHolder d'un resultat, a l'image de d'un item de la liste
 * Representation graphique
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

    static ResultViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_result_layout, parent, false);
        return new ResultViewHolder(view);
    }

    public void bind(String logo, int logoColor, String place, String libelle, int pos, int prts, double pts) {
        logoTextView.setText(logo);
        logoTextView.getBackground().setColorFilter(logoColor, PorterDuff.Mode.SRC_ATOP);
        placeTextView.setText(place);
        libelleTextView.setText(libelle);
        resultPosPrtsTextView.setText(pos + "e sur " + prts);
        resultPtsView.setText(String.valueOf(pts));

    }
}
