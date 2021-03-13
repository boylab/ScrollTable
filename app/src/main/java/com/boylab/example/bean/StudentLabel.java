package com.boylab.example.bean;

import com.boylab.protocol.ItemRect;
import com.boylab.protocol.ItemRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentLabel implements ItemRow, ItemRect {

    public static final int SIZE = 24;
    private final List<String> label = new ArrayList<String>(){{
        add("序号");
        add("姓名");
        add("年龄");
        add("性别");
        add("身高");
        add("体重");
        add("语文");
        add("数学");
        add("英语");
        add("语文老师");
        add("数学老师");
        add("英语老师");
        add("备注");
    }};

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public String get(int position) {
        position = position % label.size();
        if (position < size()){
            return label.get(position);
        }
        return "N/A";
    }

    @Override
    public int getHeight() {
        return 60;
    }

    private HashMap<Integer, Integer> itemWidth = new HashMap<Integer, Integer>(){{
        put(0, 100);
        put(1, 100);
        put(2, 100);
        put(3, 100);
        put(4, 100);
        put(5, 100);
        put(6, 100);
        put(7, 100);
        put(8, 100);
        put(9, 100);
        put(10, 100);
        put(11, 100);
        put(12, 100);
    }};

    @Override
    public int getWidth(int column) {
        return itemWidth.get(column);
    }
}
