package com.chauduong.photoeditor.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.chauduong.photoeditor.Adapter.IntroViewPagerAdapter;
import com.chauduong.photoeditor.MainActivity;
import com.chauduong.photoeditor.Manager.PrefManager;
import com.chauduong.photoeditor.R;
import com.chauduong.photoeditor.Utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chauduong.photoeditor.Adapter.IntroViewPagerAdapter.NUMBER_SLIDER;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.layoutDots)
    LinearLayout mDotsLayout;
    @BindView(R.id.btn_skip)
    Button mBtnSkip;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    private PrefManager mPrefManager;
    private IntroViewPagerAdapter mPagerAdapter;
    private TextView[] mDots;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!PrefManager.getInstance(this).isFirstTimeLaunch()) {
            launchHomeScreen();
        }

        //Making notification bar transparent
        Utils.setFullScreen(this);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);


        /**
         * Layouts of all welcome slides
         * add few more layouts if you want
         */

        addBottomDots(0);

        mPagerAdapter = new IntroViewPagerAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mBtnSkip.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);

    }


    private void addBottomDots(int currentPage) {
        mDots = new TextView[NUMBER_SLIDER];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInActive = getResources().getIntArray(R.array.array_dot_inactive);

        mDotsLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("•"));
            mDots[i].setTextSize(25);
            mDots[i].setTextColor(colorsInActive[0]);
            mDotsLayout.addView(mDots[i]);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mDots[i].getLayoutParams();
            lp.setMarginStart(getResources().getDimensionPixelSize(R.dimen.dp3));
            lp.setMarginEnd(getResources().getDimensionPixelSize(R.dimen.dp3));
            lp.gravity = Gravity.CENTER;
            mDots[i].setLayoutParams(lp);
        }
        if (mDots.length > 0) {
            mDots[currentPage].setTextColor(colorsActive[0]);
        }
    }


    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                int current = getItem(1);
                if (current < NUMBER_SLIDER) {
                    mViewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
                break;
            case R.id.btn_skip:
                launchHomeScreen();
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        addBottomDots(position);
        //Change the next button text 'NEXT'/'GOT IT'
        if (position == NUMBER_SLIDER - 1) {
            mBtnNext.setText(getString(R.string.start));
            mBtnSkip.setVisibility(View.GONE);
        } else {
            //Still pages are left
            mBtnNext.setText(getString(R.string.next));
            mBtnSkip.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}