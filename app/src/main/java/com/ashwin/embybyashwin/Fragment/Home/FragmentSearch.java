package com.ashwin.embybyashwin.Fragment.Home;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashwin.embybyashwin.R;


public class FragmentSearch extends Fragment {


    public FragmentSearch() {
        // Required empty public constructor
    }

    public static FragmentSearch newInstance() {
        FragmentSearch fragment = new FragmentSearch();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        return v;
    }
}