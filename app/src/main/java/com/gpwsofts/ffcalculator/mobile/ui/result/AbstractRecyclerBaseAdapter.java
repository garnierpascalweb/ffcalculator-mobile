package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractRecyclerBaseAdapter<VH extends RecyclerView.ViewHolder>
        extends BaseAdapter implements Filterable {
    private static final String TAG_NAME = "RecyclerBaseAdapter";
    protected final RecyclerView.Adapter<VH> mAdapter;
    public AbstractRecyclerBaseAdapter(RecyclerView.Adapter<VH> adapter) {
        mAdapter = adapter;
    }
    @Override
    public int getItemViewType(int position) {
        return mAdapter.getItemViewType(position);
    }
    @Override
    public int getCount() {
        return mAdapter.getItemCount();
    }
    @Override
    public abstract Object getItem(int position);
    @Override
    public long getItemId(int position) {
        return mAdapter.getItemId(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder;
        if (convertView == null) {
            holder = mAdapter.createViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (VH) convertView.getTag();
        }
        mAdapter.bindViewHolder(holder, position);
        return holder.itemView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                result.values = mAdapter;
                result.count = mAdapter.getItemCount();
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            };
        };
    }
}
