package com.diamonddesign.rasvo.weatherclient.managelocation;

import android.content.Context;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.diamonddesign.rasvo.weatherclient.api.LocationApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by rasvo on 11.11.2016.
 */

public class AutoCompleteAdapter extends ArrayAdapter<Location> implements Filterable{
    private ArrayList<Location> data;

    public AutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        data = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public Location getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Location location = data.get(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.manage_location_autocomplete_row, parent, false);

        TextView row = (TextView)convertView.findViewById(R.id.autocompleteTextView);
        row.setText(location.getLocationName());

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (constraint != null) {
                    OkHttpClient client = new OkHttpClient();
                    LocationApi api = new LocationApi();

                    try {
                        Response res = client.newCall(api.buildAutocompleteRequest(constraint.toString())).execute();
                        JSONArray arr = new JSONArray(res.body().string());

                        if (arr.length() > 0) {
                            data.clear();

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jLoc = arr.getJSONObject(i);
                                Location location = new Location();

                                location.setKey(jLoc.getString("Key"));
                                location.setLocalizedName(jLoc.getString("LocalizedName"));
                                JSONObject country = jLoc.getJSONObject("Country");
                                location.setCountryID(country.getString("ID"));
                                location.setCountryName(country.getString("LocalizedName"));

                                data.add(location);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    filterResults.values = data;
                    filterResults.count = data.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (data != null && data.size() > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}
