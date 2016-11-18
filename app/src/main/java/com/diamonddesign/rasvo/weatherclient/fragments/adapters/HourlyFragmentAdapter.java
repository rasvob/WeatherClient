package com.diamonddesign.rasvo.weatherclient.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.orm.HourlyCondition;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by rasvo on 17.11.2016.
 */

public class HourlyFragmentAdapter extends RecyclerView.Adapter<HourlyFragmentAdapter.HourlyViewHolder> {
    private ArrayList<HourlyCondition> data;
    private Context context;
    private UnitContext unitContext;
    private IRecyclerViewRowClicked clickedCallback;

    public HourlyFragmentAdapter(ArrayList<HourlyCondition> data, Context context, UnitContext unitContext, IRecyclerViewRowClicked callback) {
        this.data = data;
        this.context = context;
        this.unitContext = unitContext;
        this.clickedCallback = callback;
    }

    @Override
    public HourlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item, parent, false);
        final HourlyViewHolder holder = new HourlyViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedCallback.onRowClick(holder.getLayoutPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(HourlyViewHolder holder, int position) {
        HourlyCondition condition = data.get(position);

        holder.icon.setImageDrawable(condition.getIconDrawable(context));
        holder.date.setText(condition.getDayAndMonth(context));
        holder.day.setText(condition.getDayName(context));
        holder.phrase.setText(condition.getPhrase());
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String min = decimalFormat.format(unitContext.getTemperatureStrategy().getTemperature(condition));
        holder.tempMin.setText(min);
        holder.tempUnit.setText(unitContext.getTemperatureStrategy().getUnit());
        holder.precip.setText(condition.getPrecipitationProbability() + " " + context.getString(R.string.percent));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class HourlyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView date, day, phrase, tempMin, precip, tempUnit;

        public HourlyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.hourly_row_icon);
            date = (TextView) itemView.findViewById(R.id.hourly_row_date);
            day = (TextView) itemView.findViewById(R.id.hourly_row_day);
            phrase = (TextView) itemView.findViewById(R.id.hourly_row_phrase);
            tempMin = (TextView) itemView.findViewById(R.id.hourly_row_temp_max);
            precip = (TextView) itemView.findViewById(R.id.hourly_row_precip);
            tempUnit = (TextView) itemView.findViewById(R.id.hourly_row_temp_unit);
        }
    }

    public ArrayList<HourlyCondition> getData() {
        return data;
    }

    public void setData(ArrayList<HourlyCondition> data) {
        this.data = data;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UnitContext getUnitContext() {
        return unitContext;
    }

    public void setUnitContext(UnitContext unitContext) {
        this.unitContext = unitContext;
    }


}
