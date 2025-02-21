package com.gpwsofts.ffcalculator.mobile.ui.result;

import androidx.recyclerview.widget.RecyclerView;

public class TownsRecyclerBaseAdapter extends AbstractRecyclerBaseAdapter {
    public TownsRecyclerBaseAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public Object getItem(int position) {
        return ((TownsListAdapter) mAdapter).getCurrentList().get(position);
    }
}