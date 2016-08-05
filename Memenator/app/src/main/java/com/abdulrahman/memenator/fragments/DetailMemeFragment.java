package com.abdulrahman.memenator.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdulrahman.memenator.R;
import com.bumptech.glide.Glide;

import ooo.oxo.library.widget.TouchImageView;

/**
 * faruk : fragment for zooming and panning meme image
 */
public class DetailMemeFragment extends Fragment {

    private String mImageUrl = "https://cdn.meme.am/images/1232401.jpg";

    //faruk : newInstance with url as parameter
    public static DetailMemeFragment newInstance(String imageUrl) {

        Bundle args = new Bundle();

        DetailMemeFragment fragment = new DetailMemeFragment();
        fragment.setArguments(args);
        fragment.setmImageUrl(imageUrl);
        return fragment;
    }

    public DetailMemeFragment() {
        // Required empty public constructor
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meme_detail, container, false);

        TouchImageView img = (TouchImageView) rootView.findViewById(R.id.img);
        Glide.with(getActivity())
                .load(mImageUrl)
                .into(img);

        return rootView;
    }

}
