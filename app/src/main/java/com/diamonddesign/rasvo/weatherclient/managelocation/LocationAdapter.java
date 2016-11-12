package com.diamonddesign.rasvo.weatherclient.managelocation;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.enums.EntryOperation;
import com.diamonddesign.rasvo.weatherclient.managelocation.callback.LocationOperationEvent;
import com.diamonddesign.rasvo.weatherclient.managelocation.callback.OnOperationPerformed;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by rasvo on 11.11.2016.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private ArrayList<Location> locations;
    private Context context;

    public LocationAdapter(ArrayList<Location> locations) {
        this.locations = locations;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_location_list_item, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        int pos = position;
        holder.locationName.setText(location.getLocationName());

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showPopUpMenu(holder.overflow, pos);
            }
        });
    }

    private void showPopUpMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        final MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.location_overflow_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopUpMenuItemClickListenere(position));
        popupMenu.show();
    }

    class PopUpMenuItemClickListenere implements PopupMenu.OnMenuItemClickListener {
        private Location location;
        private int position;

        public PopUpMenuItemClickListenere() {

        }

        public PopUpMenuItemClickListenere(int position) {
            this.position = position;
        }

        public PopUpMenuItemClickListenere(Location location, int position) {
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
        public RelativeLayout outline;
        public ImageView favIcon;
        public TextView locationName;
        public ImageView overflow;

        public LocationViewHolder(View itemView) {
            super(itemView);
            outline = (RelativeLayout) itemView.findViewById(R.id.locationFavBackgroud);
            favIcon = (ImageView) itemView.findViewById(R.id.locationFavImage);
            locationName = (TextView) itemView.findViewById(R.id.locationName);
            overflow = (ImageView) itemView.findViewById(R.id.locationOverflow);
        }
    }
}
