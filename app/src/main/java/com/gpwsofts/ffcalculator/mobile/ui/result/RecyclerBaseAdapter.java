package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerBaseAdapter<VH extends RecyclerView.ViewHolder>
        extends BaseAdapter implements Filterable {
    private static final String TAG_NAME = "RecyclerBaseAdapter";

    private final RecyclerView.Adapter<VH> mAdapter;

    public RecyclerBaseAdapter(RecyclerView.Adapter<VH> adapter) {
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
    public Object getItem(int position) {
        // not supported
        //return null;
        return ((ClassesListAdapter)mAdapter).getCurrentList().get(position).getSpinnerItemValue();
    }



    @Override
    public long getItemId(int position) {
        return mAdapter.getItemId(position);
    }

    @SuppressWarnings("unchecked")
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
                Log.i(TAG_NAME, "filtre sur la contrainte <" + constraint + ">");
                FilterResults result = new FilterResults();
                result.values = mAdapter;
                result.count = mAdapter.getItemCount();
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // TODO Auto-generated method stub
                //Names = (ArrayList<String>) results.values;
                Log.i(TAG_NAME, "appel de publishResults avec <" + results + ">");
                notifyDataSetChanged();
            }

            ;
        };
    }
}