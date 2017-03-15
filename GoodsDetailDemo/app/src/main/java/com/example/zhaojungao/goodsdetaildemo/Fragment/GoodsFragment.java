package com.example.zhaojungao.goodsdetaildemo.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zhaojungao.goodsdetaildemo.ImageDisOps.ImageDisOptions;
import com.example.zhaojungao.goodsdetaildemo.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播效果的实现：使用handler进行更新
 * Created by zhaojun.gao on 2017/2/14.
 */
public class GoodsFragment extends Fragment {
    private View mView;
    private ViewPager mViewpager_inside;
    private List<String> mImageViewList = new ArrayList<>();
    private LinearLayout mLl_point;
    private int autoCurrIndex = 0;
    private static final int UPTATE_VIEWPAGER = 0;
    //定时轮播图片，需要在主线程里面修改 UI
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPTATE_VIEWPAGER:
                    if (msg.arg1 != 0) {
                        mViewpager_inside.setCurrentItem(msg.arg1);
                    } else {
                        //false 当从末页调到首页是，不显示翻页动画效果，
                        mViewpager_inside.setCurrentItem(msg.arg1, false);
                    }
//                    sendEmptyMessageDelayed(UPTATE_VIEWPAGER, 2000);
                    rollImage();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_goods, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mImageViewList.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1487139776&di=45f2d822751c4f34904cb4073985377c&src=http://imgsrc.baidu.com/forum/pic/item/5c7e0fb30f2442a7803a0659d143ad4bd01302c1.jpg");
        mImageViewList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487149859418&di=44b2fcd3af8a5f46bc983f22a20c945a&imgtype=0&src=http%3A%2F%2Fcdnq.duitang.com%2Fuploads%2Fitem%2F201504%2F15%2F20150415H3755_RFAM4.jpeg");
        mImageViewList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487149859417&di=73250d74f93ccdff8b86069c465f277f&imgtype=0&src=http%3A%2F%2Fpic31.nipic.com%2F20130725%2F13048517_144917634123_2.jpg");
        mImageViewList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487149859416&di=b1aef71ad22af8cb311bd605664f69ae&imgtype=0&src=http%3A%2F%2Fdown1.sucaitianxia.com%2Fpsd02%2Fpsd167%2Fpsds30915.jpg");
        mImageViewList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487149859414&di=250f01760f8f3f5a06b3301b5fb40dbf&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F84%2F16%2F82u58PICbgA.jpg");
        mLl_point = (LinearLayout) mView.findViewById(R.id.ll_point);//圆点指示器
        //原点指示器中设置原点有多少个是在获取到图片个数之后设置
        setPoint();
        mViewpager_inside = (ViewPager) mView.findViewById(R.id.viewpager_inside);
        mViewpager_inside.setAdapter(new MyPagerAdapter(mImageViewList, getActivity()));
        mViewpager_inside.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             /*   final int childCount = mLl_point.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (i == position) {
                        mLl_point.getChildAt(position).setBackgroundResource(R.drawable.pharmacy_detail_point_red);
                    } else {
                        mLl_point.getChildAt(i).setBackgroundResource(R.drawable.pharmacy_detail_point_gray);
                    }
                }*/
                //实现循环滑动
                final int pos = position % (mLl_point.getChildCount());
                for (int i = 0; i < mLl_point.getChildCount(); i++) {
                    if (i == pos) {
                        mLl_point.getChildAt(pos).setBackgroundResource(R.drawable.pharmacy_detail_point_red);
                    } else {
                        mLl_point.getChildAt(i).setBackgroundResource(R.drawable.pharmacy_detail_point_gray);
                    }
                }
                //设置全局变量，currentIndex为选中图标的 index
                autoCurrIndex = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        Timer timer= new Timer();
//        // 设置自动轮播图片，5s后执行，周期是5s
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Message message = new Message();
//                message.what = UPTATE_VIEWPAGER;
//                if (autoCurrIndex == mImageViewList.size() - 1) {//当前选中的位置是最后一个
//                    autoCurrIndex = -1;
//                }
//                message.arg1 = autoCurrIndex + 1;//传递下次选中的位置（递增）
//                mHandler.sendMessage(message);
//            }
//        }, 2000, 2000);
        rollImage();

    }

    private void rollImage() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPTATE_VIEWPAGER;
                if (autoCurrIndex == mImageViewList.size() - 1) {//当前选中的位置是最后一个
                    autoCurrIndex = -1;
                }
                message.arg1 = autoCurrIndex + 1;//传递下次选中的位置（递增）
                mHandler.sendMessage(message);
            }
        }, 2000);
    }

    /**
     * (初始化时)设置原点指示器个数
     */
    private void setPoint() {
        final int count = mImageViewList.size();
        List<ImageView> imageViewList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getActivity());
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.pharmacy_detail_point_red);
            } else {
                imageView.setBackgroundResource(R.drawable.pharmacy_detail_point_gray);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 5, 5, 5);
            mLl_point.addView(imageView, layoutParams);
        }
    }

    static class MyPagerAdapter extends PagerAdapter {
        private ImageLoader mImageLoader = ImageLoader.getInstance();//每个界面用到时候实例化
        private List<String> mStringList = new ArrayList<>();
        private Context mContext;
        private View view;

        public MyPagerAdapter(List<String> stringList, Context context) {
            mStringList = stringList;
            mContext = context;
        }

        @Override
        public int getCount() {
//            return mStringList.size();
            return 8000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
          /*  ViewParent vp =view.getParent();// 
            if (vp!=null){
                ViewGroup parent = (ViewGroup)vp;
                parent.removeView(viw);
            }*/
            final int pos = position % (mStringList.size());
            view = LayoutInflater.from(mContext).inflate(R.layout.goods_item, null);
            ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            mImageLoader.displayImage(mStringList.get(pos), iv_pic, ImageDisOptions.getDefaultImgDisOpt());
            container.addView(view);
            return view;
        }
    }
}
