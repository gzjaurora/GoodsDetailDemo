package com.example.zhaojungao.goodsdetaildemo.ImageDisOps;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * 设置图片的显示参数
 * Created by zhaojun.gao on 2017/2/15.
 */
public class ImageDisOptions {
    private static DisplayImageOptions mDefaultImgDisOpt;//默认的图片展示参数
    public static DisplayImageOptions getDefaultImgDisOpt(){
        if (mDefaultImgDisOpt == null) {
            mDefaultImgDisOpt = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
        return mDefaultImgDisOpt;
    }
}
