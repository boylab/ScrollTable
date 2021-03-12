package com.boylab.protocol;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Set;

public class ItemParams {

    public static final int TEXT_SIZE = 20;
    public static final int PADDING = 0;
    public static final int HEIGHT = 60;
    public static final int WIDTH = 160;

    //显示格式
    private int height = HEIGHT;
    private HashMap<Integer, Integer> itemWidth = new HashMap<>();

    private float textSize;
    private int textColor;
    private int backgroundColor;
    private int foucsColor;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    private ItemGravity itemGravity;

    public ItemParams() {
    }

    public ItemParams(int backgroundColor) {
        this.height = HEIGHT;
        this.textSize = 20;
        this.textColor = Color.BLACK;
        this.backgroundColor = backgroundColor;
        this.foucsColor = backgroundColor;
        this.paddingLeft = 0;
        this.paddingTop = 0;
        this.paddingRight = 0;
        this.paddingBottom = 0;

        this.itemGravity = ItemGravity.CENTER;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public HashMap<Integer, Integer> getItemWidth() {
        return itemWidth;
    }

    public int getWidth(int column) {
        if (itemWidth.containsKey(column)){
            return itemWidth.get(column);
        }
        return WIDTH;
    }

    @Deprecated
    public void setItemWidth(int column, int itemWidth) {
        this.itemWidth.put(column, itemWidth);
    }

    public void setItemWidth(HashMap<Integer, Integer> itemWidth) {
        if (itemWidth != null){
            Set<Integer> keySet = itemWidth.keySet();
            for(int key : keySet){
                this.itemWidth.put(key, itemWidth.get(key));
            }
        }
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getFoucsColor() {
        return foucsColor;
    }

    public void setFoucsColor(int foucsColor) {
        this.foucsColor = foucsColor;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public ItemGravity getItemGravity() {
        return itemGravity;
    }

    public void setItemGravity(ItemGravity itemGravity) {
        this.itemGravity = itemGravity;
    }

    @Override
    public String toString() {
        return "ItemParams{" +
                "height=" + height +
                ", itemWidth=" + itemWidth +
                ", textSize=" + textSize +
                ", textColor=" + textColor +
                ", backgroundColor=" + backgroundColor +
                ", foucsColor=" + foucsColor +
                ", paddingLeft=" + paddingLeft +
                ", paddingTop=" + paddingTop +
                ", paddingRight=" + paddingRight +
                ", paddingBottom=" + paddingBottom +
                ", itemGravity=" + itemGravity +
                '}';
    }
}
