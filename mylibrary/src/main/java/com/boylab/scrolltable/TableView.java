package com.boylab.scrolltable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

public class TableView extends LinearLayout {

    private LinearLayout layout_HeadView, childRoot;

    private SmartRefreshLayout refreshLayout;
    private SynScrollerLayout mSynScrollerview;
    private RecyclerView rv_TableView;
    private View mView;

    private ArrayList<String> strings = new ArrayList<>();

    public TableView(Context context) {
        this(context, null, 0);
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        View rootView = View.inflate(context, R.layout.layout_tableview, this);

        layout_HeadView = rootView.findViewById(R.id.layout_HeadView);
        childRoot = rootView.findViewById(R.id.ll_child_root);
        layout_HeadView.setClickable(true);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        rv_TableView = rootView.findViewById(R.id.rv_TableView);
        mSynScrollerview = rootView.findViewById(R.id.synscrollerview);


        for (int i = 0; i < 50; i++) {
            strings.add("左侧条目" + i);
        }

        for (int i = 0; i < 20; i++) {
            View inflate = View.inflate(context, R.layout.item_child_layout, null);
            TextView name = inflate.findViewById(R.id.tv);
            name.setText("类别" + i);
            childRoot.addView(inflate);
        }
        rv_TableView.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_TableView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        TableViewAdapter adapter = new TableViewAdapter(getContext(), strings, mSynScrollerview);
        rv_TableView.setAdapter(adapter);

        layout_HeadView.setOnTouchListener(getListener());
        rv_TableView.setOnTouchListener(getListener());

        mSynScrollerview.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(), "条目" + position, Toast.LENGTH_SHORT).show();
                try {
                    View viewById = view.findViewById(R.id.ll_view);
                    if (mView != null) {
                        mView.setVisibility(View.GONE);
                        viewById.setTag(null);
                    }
                    boolean b = viewById.getVisibility() == View.GONE;
                    if (b) {
                        viewById.setTag(position);
                        viewById.setVisibility(View.VISIBLE);
                        mView = viewById;
                    } else {
                        viewById.setTag(null);
                        viewById.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @NonNull
    private View.OnTouchListener getListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mSynScrollerview.onTouchEvent(motionEvent);
                return false;
            }
        };
    }

}
