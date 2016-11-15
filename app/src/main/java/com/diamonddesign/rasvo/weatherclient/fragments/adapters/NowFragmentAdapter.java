package com.diamonddesign.rasvo.weatherclient.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.models.NowGridItem;

import java.util.ArrayList;

/**
 * Created by rasvo on 15.11.2016.
 */

public class NowFragmentAdapter extends RecyclerView.Adapter<NowFragmentAdapter.NowViewHolder> {
    private ArrayList<NowGridItem> data;

    public NowFragmentAdapter(ArrayList<NowGridItem> data) {
        this.data = data;
    }

    @Override
    public NowFragmentAdapter.NowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.now_grid_item, parent, false);
        return new NowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NowFragmentAdapter.NowViewHolder holder, int position) {
        NowGridItem item = data.get(position);

        holder.header.setText(item.getHeader());
        holder.value.setText(item.getValue());
        holder.unit.setText(item.getUnit());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NowViewHolder extends RecyclerView.ViewHolder {
        TextView header, value, unit;
        NowViewHolder(View itemView) {
            super(itemView);

            header = (TextView) itemView.findViewById(R.id.now_item_header);
            value = (TextView) itemView.findViewById(R.id.now_item_value);
            unit = (TextView) itemView.findViewById(R.id.now_item_unit);
        }
    }
}
