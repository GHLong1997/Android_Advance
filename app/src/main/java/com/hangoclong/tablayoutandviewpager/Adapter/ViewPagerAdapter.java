package com.hangoclong.tablayoutandviewpager.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.hangoclong.tablayoutandviewpager.Fragment.Fragment;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mListFragments;
    private String[] mTitleFragment;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] title) {
        super(fm);
        mListFragments = fragmentList;
        mTitleFragment = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragments.get(position);
    }

    @Override
    public int getCount() {
        return mListFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleFragment[position];
    }

}
