package com.abdulrahman.memenator.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.abdulrahman.memenator.R;
import com.abdulrahman.memenator.adapters.HomeTabViewPagerAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;

import java.util.ArrayList;

/*
    faruk : this activity displaying 3 tabs,
    1. tab for list of recent meme from all over the world
    2. tab for list of meme character
    3. tab for list of user personal meme
 */
public class HomeActivity extends AppCompatActivity {

    private Fragment currentFragment;
    private HomeTabViewPagerAdapter adapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    // UI
    private AHBottomNavigationViewPager viewPager;
    private AHBottomNavigation bottomNavigation;

    private int mTabPosition = -1;
    private int mFirstTabPosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //faruk : set the toolbar title to empty just in case
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Memenator");
        setSupportActionBar(mToolbar);

        initUI();
    }

    /*
       faruk : method for init UI, all the view here
     */
    private void initUI() {
        //faruk : initialize view
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        viewPager = (AHBottomNavigationViewPager) findViewById(R.id.view_pager);

        //faruk : create items for TAB
        AHBottomNavigationItem navItemRecent = new AHBottomNavigationItem("Recents",
                ContextCompat.getDrawable(this, R.drawable.pinpoint),
                ContextCompat.getColor(this, R.color.colorPrimary));

        AHBottomNavigationItem navItemCharacters = new AHBottomNavigationItem("Characters",
                ContextCompat.getDrawable(this, R.drawable.home),
                ContextCompat.getColor(this, R.color.colorPrimary));

        AHBottomNavigationItem navItemPersonal = new AHBottomNavigationItem("Personal",
                ContextCompat.getDrawable(this, R.drawable.profile),
                ContextCompat.getColor(this, R.color.colorPrimary));

        bottomNavigationItems.add(navItemRecent);
        bottomNavigationItems.add(navItemCharacters);
        bottomNavigationItems.add(navItemPersonal);


        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.colorAccent));
        bottomNavigation.setInactiveColor(ContextCompat.getColor(this, R.color.colorInactive));
        bottomNavigation.setColored(true);
        //faruk : for the first time, which tab selected set here
        bottomNavigation.setCurrentItem(mFirstTabPosition);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position, false);
                currentFragment = adapter.getCurrentFragment();

                return true;
            }
        });

        //faruk: number of fragment that kept in memory
        viewPager.setOffscreenPageLimit(2);
        adapter = new HomeTabViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTabPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //faruk : for the first time, which fragment to display set here
        viewPager.setCurrentItem(mFirstTabPosition);

        currentFragment = adapter.getCurrentFragment();

        //faruk : testing for notification
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                bottomNavigation.setNotification("7", 2);
//            }
//        }, 3000);

    }


}
