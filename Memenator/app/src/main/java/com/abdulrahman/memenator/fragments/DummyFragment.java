package com.abdulrahman.memenator.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdulrahman.memenator.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DummyFragment extends Fragment {


    public DummyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dummy, container, false);

        return rootView;
    }

}
