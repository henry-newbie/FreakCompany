package com.henry.freakcompany.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.henry.freakcompany.ui.fragment.MainFragment;

import java.util.List;

/**
 * Created by henry on 2016/6/12.
 */
public class MainAdapter extends FragmentPagerAdapter{

    List<Fragment> fragmentList;

    String[] titles;

    public MainAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null?0 : fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
