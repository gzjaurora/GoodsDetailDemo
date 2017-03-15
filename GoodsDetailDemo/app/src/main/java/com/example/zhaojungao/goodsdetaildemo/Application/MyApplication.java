package com.example.zhaojungao.goodsdetaildemo.Application;

import android.app.Application;
import android.content.Context;

import com.example.zhaojungao.goodsdetaildemo.Utils.Utils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * Created by zhaojun.gao on 2017/2/15.
 */
public class MyApplication extends Application {
     
    public static final String IMAGE_LOADER = "image";

    @Override
    public void onCreate() {
        super.onCreate();
        //配置ImageLoaderConfiguration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCache(new UnlimitedDiskCache(new File(getCachePath(getApplicationContext(), IMAGE_LOADER))))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .threadPoolSize(3)
                .memoryCache(new LRULimitedMemoryCache(1 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheExtraOptions(1080, 1080)
                .build();
        //全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

    public static String getCachePath(Context mContext, String folderName) {
        return Utils.getDiskFilesDir(mContext, folderName);
    }
}
