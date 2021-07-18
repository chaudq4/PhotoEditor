package com.chauduong.photoeditor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.chauduong.photoeditor.R;

public class IntroViewPagerAdapter extends PagerAdapter {
    public static final int NUMBER_SLIDER=4;
    private LayoutInflater mInflater;
    Context mContext;
    private int[] mLayouts;

    public IntroViewPagerAdapter(Context mContext) {
        super();
        this.mContext = mContext;
        mLayouts = new int[]{R.layout.welcome_slider1, R.layout.welcome_slider2, R.layout.welcome_slider3, R.layout.welcome_slider4};
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(mLayouts[position], container, false);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mLayouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}