package com.boylab.scrolltable;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boylab.HeadAdapter;

import java.util.List;

/**
 * -------- 日期 ---------- 维护人 ------------ 变更内容 --------
 * 2018/9/14    16:55	    刘泽			    新增 类
 * 2018/9/14	16:55	    刘泽			    增加yyy属性
 */

public class TableViewAdapter extends RecyclerView.Adapter<TableViewAdapter.ScrollViewHolder> {

    private Context context;
    private final SynScrollerLayout mSynScrollerview;
    private final List<String> mData;

    public TableViewAdapter(Context context, List<String> data, SynScrollerLayout synScrollerview) {
        context = context;
        mSynScrollerview = synScrollerview;
        mData = data;
    }

    @Override
    public ScrollViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = View.inflate(viewGroup.getContext(), R.layout.item_scroll_layout2, null);


        return new ScrollViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ScrollViewHolder holder, final int position) {

        holder.mView.setText(mData.get(position));

        mSynScrollerview.setOnScrollListener(new SynScrollerLayout.OnItemScrollView() {
            @Override
            public void OnScroll(int l, int t, int oldl, int oldt) {
                holder.mSynScrollerLayout.smoothScrollTo(l, 0);
            }

        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mSynScrollerview.onTouchEvent(v, position, event);
                return false;
            }
        });
        holder.ll_view.setVisibility(holder.ll_view.getTag() == null ? View.GONE:(((int) holder.ll_view.getTag())==position? View.VISIBLE: View.GONE));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ScrollViewHolder extends RecyclerView.ViewHolder {

        public final TextView mView;
        public final SynScrollerLayout mSynScrollerLayout;
        public final LinearLayout mChildRoot;
        public final LinearLayout ll_view;

        public ScrollViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.tv);
            mSynScrollerLayout = itemView.findViewById(R.id.synscrollerview);
            mChildRoot = itemView.findViewById(R.id.ll_child_root);

            ll_view = itemView.findViewById(R.id.ll_view);
            for (int i = 0; i < 20; i++) {
                View inflate = View.inflate(itemView.getContext(), R.layout.item_child_layout, null);
                TextView name = inflate.findViewById(R.id.tv);
                name.setText("内容" + i);
                mChildRoot.addView(inflate);
            }
        }
    }
}
