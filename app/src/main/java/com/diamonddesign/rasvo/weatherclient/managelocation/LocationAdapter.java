package com.diamonddesign.rasvo.weatherclient.managelocation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.enums.EntryOperation;
import com.diamonddesign.rasvo.weatherclient.managelocation.callback.LocationOperationEvent;
import com.diamonddesign.rasvo.weatherclient.orm.Location;

import java.util.ArrayList;

/**
 * Created by rasvo on 11.11.2016.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private ArrayList<Location> locations;
    private Context context;

    public LocationAdapter(ArrayList<Location> locations, Context context) {
        this.locations = locations;
        this.context = context;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_location_list_item, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LocationViewHolder holder, int position) {
        final Location location = locations.get(position);
        final int pos = position;
        holder.locationName.setText(location.getLocationName());

        if (location.isFavourite()) {
            Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_favorite_white_24px);
            holder.favIcon.setImageDrawable(icon);
        }
        else {
            Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_white_24px);
            holder.favIcon.setImageDrawable(icon);
        }

        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                org.greenrobot.eventbus.EventBus.getDefault().post(new LocationOperationEvent(location, pos, EntryOperation.FAVOURITE));
            }
        });

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpMenu(holder.overflow, pos, location);
            }
        });
    }

    private void showPopUpMenu(View view, int position, Location location) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        final MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.location_overflow_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopUpMenuItemClickListenere(position, location));
        popupMenu.show();
    }

    class PopUpMenuItemClickListenere implements PopupMenu.OnMenuItemClickListener {
        private Location location;
        private int position;


        public PopUpMenuItemClickListenere(int position, Location location) {
            this.location = location;
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();

            switch (id) {
                case R.id.action_locations_favourite:
                    org.greenrobot.eventbus.EventBus.getDefault().post(new LocationOperationEvent(location, position, EntryOperation.FAVOURITE));
                    return true;

                case R.id.action_locations_remove:
                    org.greenrobot.eventbus.EventBus.getDefault().post(new LocationOperationEvent(location, position, EntryOperation.REMOVE));
                    return true;
            }
            return false;
        }
    }
    public int getItemCount() {
        return locations.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        public ImageView favIcon;
        public TextView locationName;
        public ImageView overflow;

        public LocationViewHolder(View itemView) {
            super(itemView);
            favIcon = (ImageView) itemView.findViewById(R.id.locationFavImage);
            locationName = (TextView) itemView.findViewById(R.id.locationName);
            overflow = (ImageView) itemView.findViewById(R.id.locationOverflow);
        }
    }
}
