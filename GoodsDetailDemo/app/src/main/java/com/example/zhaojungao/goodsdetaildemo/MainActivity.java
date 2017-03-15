package com.example.zhaojungao.goodsdetaildemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhaojungao.goodsdetaildemo.Fragment.DetailFragment;
import com.example.zhaojungao.goodsdetaildemo.Fragment.GoodsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 为什么没有滑动冲突：viewpager内部处理了
 *  by gzjaurora
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_goods) {
            mViewpager_outside.setCurrentItem(0);
        } else if (v.getId() == R.id.ll_detail) {
            mViewpager_outside.setCurrentItem(1);
        }
    }

    private ViewPager mViewpager_outside;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private TextView mTv_goods;
    private TextView mTv_detail;
    private View mView_goods;
    private View mView_detail;
    private GoodsFragment goodsfragment;
    private DetailFragment detailfragment;
    private LinearLayout mLl_goods;
    private LinearLayout mLl_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        List<Fragment> fragmentList = new ArrayList<>();
        goodsfragment = new GoodsFragment();
        fragmentList.add(goodsfragment);
        detailfragment = new DetailFragment();
        fragmentList.add(detailfragment);

        mLl_goods = (LinearLayout) findViewById(R.id.ll_goods);
        mLl_goods.setOnClickListener(this);
        mLl_detail = (LinearLayout) findViewById(R.id.ll_detail);
        mLl_detail.setOnClickListener(this);
        mTv_goods = (TextView) findViewById(R.id.tv_goods);
        mView_goods = findViewById(R.id.view_goods);
        mTv_detail = (TextView) findViewById(R.id.tv_detail);
        mView_detail = findViewById(R.id.view_detail);
        mViewpager_outside = (ViewPager) findViewById(R.id.viewpager_outside);
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(fragmentList, getSupportFragmentManager());
        mViewpager_outside.setAdapter(mMyFragmentPagerAdapter);

        mViewpager_outside.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //重置顶部视图
                //如何优化？
                if (position == 0) {
                    mTv_goods.setTextColor(getApplicationContext().getResources().getColor(R.color.pharmacy_blue));
                    mView_goods.setVisibility(View.VISIBLE);

                    mTv_detail.setTextColor(getApplicationContext().getResources().getColor(R.color.pharmacy_black));
                    mView_detail.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
                    mTv_goods.setTextColor(getApplicationContext().getResources().getColor(R.color.pharmacy_black));
                    mView_goods.setVisibility(View.INVISIBLE);

                    mTv_detail.setTextColor(getApplicationContext().getResources().getColor(R.color.pharmacy_blue));
                    mView_detail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    static class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<Fragment>();

        public MyFragmentPagerAdapter(List<Fragment> fragmentList, FragmentManager fm) {
            super(fm);
            fragments = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
//    static class MyPagerAdapter extends PagerAdapter{
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return false;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
////            return super.instantiateItem(container, position);
//            container.addView();
//            return 
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
//        }
//    }
}
