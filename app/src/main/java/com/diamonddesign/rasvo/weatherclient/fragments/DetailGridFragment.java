package com.diamonddesign.rasvo.weatherclient.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.fragments.adapters.NowFragmentAdapter;
import com.diamonddesign.rasvo.weatherclient.models.NowGridItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailGridFragment extends Fragment {
    private ArrayList<NowGridItem> items = new ArrayList<>();
    private NowFragmentAdapter adapter;
    private RecyclerView recyclerView;

    public DetailGridFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_grid, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.detailGridRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new NowFragmentAdapter(items);
        recyclerView.setAdapter(adapter);
        return view;
    }


    public void setData(ArrayList<NowGridItem> data) {
        items.clear();
        items.addAll(data);
    }

    public void refreshGridView() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
