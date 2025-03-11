package com.gpwsofts.ffcalculator.mobile.ui.result;

import androidx.recyclerview.widget.RecyclerView;

public class StringRecyclerBaseAdapter extends AbstractRecyclerBaseAdapter {
    public StringRecyclerBaseAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public Object getItem(int position) {
        return ((StringListAdapter) mAdapter).getCurrentList().get(position).toString();
    }
}
