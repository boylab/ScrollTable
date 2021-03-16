package com.boylab.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DensityUtil {

    private static DensityUtil instance;
    private float density;
    private float fontScale;

    public static synchronized DensityUtil initDensity(Context context) {
        if (instance == null) {
            instance = new DensityUtil(context);
        }
        return instance;
    }

    private DensityUtil(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.density = displayMetrics.density;
        this.fontScale = displayMetrics.scaledDensity;
    }

    public static DensityUtil newInstance() {
        if (instance == null){
            throw new NullPointerException("You should use initDensity(Context context) first!");
        }
        return instance;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        return (int) (dpValue * this.density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue) {
        return (int) (pxValue / this.density + 0.5f);
    }


    /**
     *  将px值转换为sp值，保证文字大小不变
     * @param pxValue
     * @return
     */
    public int px2sp(float pxValue) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public int sp2px(float spValue) {
        return (int) (spValue * fontScale + 0.5f);
    }
}
