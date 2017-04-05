package com.learning.words;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learning.words.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqilin on 2017/2/17.
 */

public class WordPagerAdapter extends FragmentStatePagerAdapter {

    private List<Word> mWords;
    private SparseArray<Fragment> mFragments = new SparseArray<>();

    public WordPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = WordDetailHeadFragment.newInstance(mWords.get(position));
        mFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mFragments.remove(position);
    }

    @Override
    public int getCount() {
        return mWords.size();
    }

    public void setData(List<Word> words) {
        mWords = words;
    }

    public Fragment getFragment(int index) {
        return mFragments.get(index);
    }

    public void onScrollFraction(int index, float fraction) {
        Fragment fragment = getFragment(index);
        if (fragment != null && fragment instanceof WordDetailHeadFragment) {
            ((WordDetailHeadFragment) fragment).onHeadScroll(fraction);
        }
    }
}
