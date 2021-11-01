package com.boylab.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView分割线
 * 可实现大部分标准列表显示
 */
public class RecyclerUtil {

    /**
     * RecyclerView 垂直 LayoutManager
     * @param context
     * @return
     */
    public static RecyclerView.LayoutManager verticalManager(Context context){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        return layoutManager;
    }

    /**
     * RecyclerView 水平 LayoutManager
     * @param context
     * @return
     */
    public static RecyclerView.LayoutManager horizontalManager(Context context){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        return layoutManager;
    }

    /**
     * RecyclerView 水平 GridLayoutManager
     * @param context
     * @param spanCount
     * @return
     */
    public static RecyclerView.LayoutManager gridLayoutManager(Context context, int spanCount){
        GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
        return layoutManager;
    }

    /**
     * 垂直RecyclerView分割线
     * @param context
     * @param color     argb颜色
     * @param height
     */
    public static RecyclerView.ItemDecoration verticalDivider(Context context, int color, int height){
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        GradientDrawable dividerDrawable = new GradientDrawable();
        dividerDrawable.setColor(color);
        dividerDrawable.setSize(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        horizontalDecoration.setDrawable(dividerDrawable);
        return horizontalDecoration;
    }

    /**
     * 水平RecyclerView分割线
     * @param context
     * @param color    argb颜色
     * @param width
     */
    public static RecyclerView.ItemDecoration horizontalDivider(Context context, int color, int width){
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
        GradientDrawable dividerDrawable = new GradientDrawable();
        dividerDrawable.setColor(color);
        dividerDrawable.setSize(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        horizontalDecoration.setDrawable(dividerDrawable);
        return horizontalDecoration;
    }

    /**
     * <>为什么是add？</>
     * 注意：recyclerView.addItemDecoration(@NonNull ItemDecoration decor)
     * 即：分开添加verticalDivider、horizontalDivider即可
     */
    private void gridDivider(){
        /**
         * 例如：
         * recyclerView.addItemDecoration(RecyclerDivider.horizontalDivider(this, Color.GRAY, 3));
         * recyclerView.addItemDecoration(RecyclerDivider.verticalDivider(this, Color.GRAY, 3));
         */
    }

}
