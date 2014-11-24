package com.gezbox.windmap.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.gezbox.windmap.app.fragment.DiscoverFragment;
import com.gezbox.windmap.app.fragment.HomeFragment;
import com.gezbox.windmap.app.fragment.MessageFragment;
import com.gezbox.windmap.app.fragment.TaskFragment;

/**
 * Created by zombie on 14-10-29.
 */
public class HomeCardFragmentPagerAdapter extends FragmentPagerAdapter {
    public static final int FRAGMENT_COUNT = 4;

    public HomeCardFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();
        }
        if (position == 1) {
            return new TaskFragment();
        }
        if (position == 2) {
            return new DiscoverFragment();
        }
        if (position == 3) {
            return new MessageFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}
