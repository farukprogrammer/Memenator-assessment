package com.abdulrahman.memenator.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdulrahman.memenator.R;
import com.abdulrahman.memenator.activities.CustomizeMemeActivity;
import com.abdulrahman.memenator.activities.MemeDetailActivity;
import com.abdulrahman.memenator.models.Meme;
import com.abdulrahman.memenator.viewholders.MemeViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * faruk : this is abstract fragment for base list fragment using recyclerview
 *      the query of this fragment can be specified using getQuery method
 */
public abstract class BaseListFragment extends Fragment {

    private static final String TAG = BaseListFragment.class.getSimpleName();

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Meme, MemeViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public BaseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_list_character, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.rv_char_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query memesQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Meme, MemeViewHolder>(Meme.class, R.layout.item_character,
                MemeViewHolder.class, memesQuery) {
            @Override
            protected void populateViewHolder(final MemeViewHolder viewHolder, final Meme model, final int position) {
                final DatabaseReference memeRef = getRef(position);

                // Set click listener for the whole meme view
                final String memeKey = memeRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //faruk: Launch MemeDetailActivity
                        Intent intent = new Intent(getActivity(), MemeDetailActivity.class);
                        intent.putExtra(MemeDetailActivity.EXTRA_MEME_KEY, model.memeUrl);
                        startActivity(intent);
                    }
                });

                viewHolder.bindToMeme(getActivity(), model, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //faruk: Launch CustomizeMemeActivity
                        Intent intent = new Intent(getActivity(), CustomizeMemeActivity.class);
                        intent.putExtra(CustomizeMemeActivity.EXTRA_MEME_URL, model.memeUrl);
                        startActivity(intent);
                    }
                });

            }
        };
        mRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}
