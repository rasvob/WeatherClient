package com.diamonddesign.rasvo.weatherclient.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by rasvo on 15.11.2016.
 */

public class DailyFragmentAdapter extends RecyclerView.Adapter<DailyFragmentAdapter.DailyViewHolder> {
    private ArrayList<DailyConditions> data;
    private Context context;
    private UnitContext unitContext;

    public DailyFragmentAdapter(ArrayList<DailyConditions> data, Context context, UnitContext unitContext) {
        this.data = data;
        this.context = context;
        this.unitContext = unitContext;
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_list_item, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {
        DailyConditions daily = data.get(position);

        holder.icon.setImageDrawable(daily.getIconDrawable(context, true));
        holder.date.setText(daily.getDayAndMonth(context));
        holder.day.setText(daily.getDayName(context));
        holder.phrase.setText(daily.getDayPhrase());

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String min = decimalFormat.format(unitContext.getTemperatureStrategy().getTemperatureMin(daily));
        String max = decimalFormat.format(unitContext.getTemperatureStrategy().getTemperatureMax(daily));

        holder.tempMin.setText(min);
        holder.tempMax.setText(max);
        holder.tempUnit.setText(unitContext.getTemperatureStrategy().getUnit());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DailyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView date, day, phrase, tempMin, tempMax, tempUnit;

        DailyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.daily_row_icon);
            date = (TextView) itemView.findViewById(R.id.daily_row_date);
            day = (TextView) itemView.findViewById(R.id.daily_row_day);
            phrase = (TextView) itemView.findViewById(R.id.daily_row_phrase);
            tempMin = (TextView) itemView.findViewById(R.id.daily_row_temp_min);
            tempMax = (TextView) itemView.findViewById(R.id.daily_row_temp_max);
            tempUnit = (TextView) itemView.findViewById(R.id.daily_row_temp_unit);
        }
    }

    public UnitContext getUnitContext() {
        return unitContext;
    }

    public void setUnitContext(UnitContext unitContext) {
        this.unitContext = unitContext;
    }
}
