package com.gpwsofts.ffcalculator.mobile.ui.result;

import androidx.recyclerview.widget.RecyclerView;

public class ClassesRecyclerBaseAdapter extends AbstractRecyclerBaseAdapter{
    public ClassesRecyclerBaseAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public Object getItem(int position) {
      return ((ClassesListAdapter) mAdapter).getCurrentList().get(position).getSpinnerItemValue();
    }
}
