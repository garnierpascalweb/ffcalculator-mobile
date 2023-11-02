package com.gpwsofts.ffcalculator.mobile.ui.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        // to set data to textview and imageview of each card layout
        IResult result = resultsList.getValue().get(position);
        // holder.idIVResultImage.setImageResource(R.drawable.ic_action_open_2_3);
        // R.
        holder.idTVResultPlace.setText(result.getPlace());
        holder.idTVResultLibelle.setText(result.getLibelle());
        //ColorFilter cf = ColorFilter.
        //TODO 1.0.0 changer dynamiquement la couleur du cercle en fonction de la caté
        // holder.idTVResultLibelle.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        holder.idTVResultPosPrts.setText(new StringBuilder().append(result.getPos()).append("e sur ").append(result.getPrts()).toString());
        //TODO pts a variabiliser
        holder.idTVResultPts.setText(new StringBuilder().append(String.valueOf(result.getPts())).append(" pts"));
    }


    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return resultsList.getValue().size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        // private final ImageView idIVResultImage;
        private final TextView idTVResultPlace;
        private final TextView idTVResultLibelle;
        private final TextView idTVResultPosPrts;
        private final TextView idTVResultPts;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            // idIVResultImage = itemView.findViewById(R.id.idIVResultImage);
            idTVResultPlace = itemView.findViewById(R.id.idTVResultPlace);
            idTVResultLibelle = itemView.findViewById(R.id.idTVResultLibelle);
            idTVResultPosPrts = itemView.findViewById(R.id.idTVResultPosPrts);
            idTVResultPts = itemView.findViewById(R.id.idTVResultPts);
        }
    }
}
