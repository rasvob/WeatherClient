package com.diamonddesign.rasvo.weatherclient.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.fragments.adapters.NowFragmentAdapter;
import com.diamonddesign.rasvo.weatherclient.models.NowGridItem;

import java.util.ArrayList;

public class NowFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NowFragmentAdapter adapter;
    private ArrayList<NowGridItem> data = new ArrayList<>();

    public NowFragment() {

    }

    public static NowFragment newInstance() {
        return new NowFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 9; i++) {
            NowGridItem item = new NowGridItem("Header: " + i, String.valueOf(i), " C");
            data.add(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.nowSwipeRefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.nowRecyclerView);
        adapter = new NowFragmentAdapter(data);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        refreshLayout.setOnRefreshListener(this);

        return view;
    }

    public ArrayList<NowGridItem> getGridItems() {
        return data;
    }

    public void refreshData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
        refreshLayout.setRefreshing(false);
    }
}
