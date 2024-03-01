package com.gpwsofts.ffcalculator.mobile.ui.result;

import androidx.recyclerview.widget.RecyclerView;

public class PosRecyclerBaseAdapter extends AbstractRecyclerBaseAdapter{
    public PosRecyclerBaseAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public Object getItem(int position) {
        return ((PosListAdapter) mAdapter).getCurrentList().get(position).toString();
    }
}
