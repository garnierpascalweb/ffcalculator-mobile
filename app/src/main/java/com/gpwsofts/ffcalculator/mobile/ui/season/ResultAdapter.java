package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.dao.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private static final String TAG_NAME = "ResultAdapter";

    //private final LiveData<List<Result>> resultsList;
    // cf. Part 6, 09:40
    private List<Result> results = new ArrayList<Result>();

    // Constructor


    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_result_layout, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        Result currentResult = results.get(position);
        holder.idTVResultPlace.setText(currentResult.getPlace());
        holder.idTVResultLibelle.setText(new StringBuilder().append(currentResult.getLibelle()).append(" - (").append(currentResult.getIdClasse()).append(")").toString());
        holder.idTVResultLogo.setText(currentResult.getLogo());
        int color = this.getLogoColor(currentResult.getLogo());
        holder.idTVResultLogo.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        holder.idTVResultPosPrts.setText(new StringBuilder().append(currentResult.getPos()).append("e sur ").append(currentResult.getPrts()).toString());
        holder.idTVResultPts.setText(new StringBuilder().append(String.valueOf(currentResult.getPts())).append(" pts"));
    }

    /**
     * calcule la couleur du cercle en fonction de la valeur du logo
     * @param logo
     * @return la couleur de la pastille
     */
    //TOTO 1.0.0 a ne pas mettre ici
    protected int getLogoColor(String logo){
        int color;
        // couleurs principales
        switch (logo){
            case "Elite" : {
                color = Color.rgb(255, 51, 0);
                break;
            }
            case "Open 1/2/3" : {
                color = Color.rgb(0, 0, 255);
                break;
            }
            case "Open 1/2" : {
                color = Color.rgb(0, 0, 255);
                break;
            }
            case "Open 2/3" : {
                color = Color.rgb(0, 102, 255);
                break;
            }
            case "Open 3" : {
                color = Color.rgb(51, 153, 255);
                break;
            }
            case "U23" : {
                color = Color.rgb(153, 255, 102);
                break;
            }
            case "U19" : {
                color = Color.rgb(204, 255, 102);
                break;
            }
            case "U17" : {
                color = Color.rgb(255, 255, 102);
                break;
            }
            default : {
                color = Color.rgb(255, 255, 255);
            }
        }
        return color;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Result> results){
        this.results = results;
        //TODO 1.0.0 moi pas comprendre mais notifyDataSetChanged ne doit pas etre utilisée ici
        // PART 6 13:45
        notifyDataSetChanged();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        // private final ImageView idIVResultImage;
        /**
         * Lieu de la course
         */
        private final TextView idTVResultPlace;
        /**
         * Textview avec cercle et couleur
         * Logo de la course, reprend le champ logo
         */
        private final TextView idTVResultLogo;
        /**
         * Libelle de la course
         * Concatenation du libelle et de la classe entre parentheses
         * Exemple : Open 2/3 Access (1.25.0)
         */
        private final TextView idTVResultLibelle;
        /**
         * Textview permettant de détailler le résultat
         * pos sur prts
         */
        private final TextView idTVResultPosPrts;
        /**
         * Points de la course
         */
        private final TextView idTVResultPts;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            idTVResultPlace = itemView.findViewById(R.id.idTVResultPlace);
            idTVResultLogo = itemView.findViewById(R.id.idTVResultLogo);
            idTVResultLibelle = itemView.findViewById(R.id.idTVLibelle);
            idTVResultPosPrts = itemView.findViewById(R.id.idTVResultPosPrts);
            idTVResultPts = itemView.findViewById(R.id.idTVResultPts);
        }
    }
}
