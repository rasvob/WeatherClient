package com.diamonddesign.rasvo.weatherclient.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;

import java.util.ArrayList;

/**
 * Created by rasvo on 15.11.2016.
 */

public class DailyFragmentAdapter extends RecyclerView.Adapter<DailyFragmentAdapter.DailyViewHolder> {
    private ArrayList<DailyConditions> data;
    private Context context;

    public DailyFragmentAdapter(ArrayList<DailyConditions> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_daily, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class DailyViewHolder extends RecyclerView.ViewHolder {
        public DailyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
