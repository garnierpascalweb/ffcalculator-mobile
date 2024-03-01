package com.gpwsofts.ffcalculator.mobile.ui.result;

import androidx.recyclerview.widget.RecyclerView;

public class IntegerRecyclerBaseAdapter extends AbstractRecyclerBaseAdapter{
    public IntegerRecyclerBaseAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public Object getItem(int position) {
        return ((IntegerListAdapter) mAdapter).getCurrentList().get(position).toString();
    }
}
