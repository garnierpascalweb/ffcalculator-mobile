package com.gpwsofts.ffcalculator.mobile.ui.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.model.IResult;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private static final String TAG_NAME = "ResultAdapter";
    private final Context context;
    private final LiveData<List<IResult>> resultsList;

    // Constructor
    public ResultAdapter(Context context, LiveData<List<IResult>> courseModelArrayList) {
        this.context = context;
        this.resultsList = courseModelArrayList;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_result_layout, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        IResult result = resultsList.getValue().get(position);
        holder.idTVResultPlace.setText(result.getPlace());
        holder.idTVResultLibelle.setText(new StringBuilder().append(result.getLibelle()).append(" - (").append(result.getIdClasse()).append(")").toString());
        holder.idTVResultLogo.setText(result.getLogo());
        int color = this.getLogoColor(result.getLogo());
        holder.idTVResultLogo.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        holder.idTVResultPosPrts.setText(new StringBuilder().append(result.getPos()).append("e sur ").append(result.getPrts()).toString());
        //TODO pts a variabiliser
        holder.idTVResultPts.setText(new StringBuilder().append(String.valueOf(result.getPts())).append(" pts"));
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
        // this method is used for showing number of card items in recycler view
        return resultsList.getValue().size();
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
