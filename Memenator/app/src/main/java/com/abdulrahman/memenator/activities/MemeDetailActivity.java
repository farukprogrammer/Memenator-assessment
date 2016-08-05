package com.abdulrahman.memenator.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.abdulrahman.memenator.R;
import com.abdulrahman.memenator.fragments.CustomizeMemeFragment;
import com.abdulrahman.memenator.fragments.DetailMemeFragment;

public class MemeDetailActivity extends AppCompatActivity {

    public static String EXTRA_MEME_KEY = "meme_key";


    private FragmentManager mFragManager;
    private FragmentTransaction mFragTrans;

    private DetailMemeFragment mDetailMemeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_meme);

        //faruk : set the toolbar title to empty just in case
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Meme Detail (Zoom and Pan)");
        setSupportActionBar(mToolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);

        mFragManager = getSupportFragmentManager();

        String memeUrl = getIntent().getStringExtra(EXTRA_MEME_KEY);
        mDetailMemeFragment = DetailMemeFragment.newInstance(memeUrl);
        mFragTrans = mFragManager.beginTransaction();
        mFragTrans.add(R.id.fl_main, mDetailMemeFragment);
        mFragTrans.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
