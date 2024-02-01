package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.content.Context;
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

    /**
     * Context pour recuperer les ressources
     * https://stackoverflow.com/questions/41007837/how-to-use-getresources-on-a-adapter-java
     */
    private Context context;
    // Constructor


    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_result_layout, parent, false);
        context = parent.getContext();
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        final Result currentResult = results.get(position);
        final String currentResultIdClasse = currentResult.getIdClasse();
        holder.idTVResultPlace.setText(currentResult.getPlace());
        holder.idTVResultLibelle.setText(new StringBuilder().append(currentResult.getLibelle()).append(" - (").append(currentResultIdClasse).append(")").toString());
        holder.idTVResultLogo.setText(currentResult.getLogo());
        holder.idTVResultLogo.getBackground().setColorFilter(currentResult.getLogoColor(), PorterDuff.Mode.SRC_ATOP);
        holder.idTVResultPosPrts.setText(new StringBuilder().append(currentResult.getPos()).append("e sur ").append(currentResult.getPrts()).toString());
        holder.idTVResultPts.setText(new StringBuilder().append(String.valueOf(currentResult.getPts())).append(" pts"));
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
