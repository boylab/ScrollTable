package com.boylab.example.bean;

import com.boylab.protocol.ItemRect;
import com.boylab.protocol.ItemRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentRect implements ItemRect {

    private List<Integer> itemWidth = new ArrayList<Integer>(){{
        add(100);
        add(100);
        add(100);
        add(100);
        add(100);
        add(100);
        add(100);
        add(100);
        add(100);
        add(100);
        add(100);
        add(100);
        add(100);
    }};

    @Override
    public int getHeight() {
        return 60;
    }

    @Override
    public int getWidth(int column) {
        return itemWidth.get(column);
    }
}
