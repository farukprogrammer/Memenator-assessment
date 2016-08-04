package com.abdulrahman.memenator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.abdulrahman.memenator.fragments.CustomizeMemeFragment;
import com.abdulrahman.memenator.fragments.DummyFragment;
import java.util.ArrayList;

/**
 * faruk : FragmentPagerAdapter for BottomTabNavigation
 */
public class HomeTabViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = HomeTabViewPagerAdapter.class.getSimpleName();

    private Fragment mFragmentAtPos0;
    private FragmentManager mFragmentManager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;

    public HomeTabViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;

        fragments.clear();
        fragments.add(new DummyFragment());
        fragments.add(new CustomizeMemeFragment());
        fragments.add(new DummyFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    /**
     * Get the current fragment
     */
    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
    }


}