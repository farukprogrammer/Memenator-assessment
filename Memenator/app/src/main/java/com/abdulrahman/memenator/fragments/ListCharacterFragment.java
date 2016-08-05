package com.abdulrahman.memenator.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdulrahman.memenator.R;
import com.abdulrahman.memenator.helpers.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListCharacterFragment extends BaseListFragment {


    public ListCharacterFragment() {
        // Required empty public constructor
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query memeCharacterQuery = databaseReference.child("memechars")
                .limitToFirst(100);
        // [END recent_posts_query]

        return memeCharacterQuery;
    }


}
