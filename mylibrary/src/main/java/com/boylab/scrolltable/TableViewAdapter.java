package com.boylab.scrolltable;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boylab.protocol.ItemRow;

import java.util.ArrayList;

public class TableViewAdapter extends RecyclerView.Adapter<TableViewAdapter.ScrollViewHolder> {

    private Context context;
    private SynScrollerLayout mSynScrollerview;
    private ArrayList<? extends ItemRow> mTableDatas = new ArrayList<>();
    private TableParams leftParams, contentParams;

    private int sizeColumn = 0;

    private LinearLayout cacheLayout ;
    private int focusRow = -1;
    private int focusColumn = -1;

    public TableViewAdapter(Context context, ArrayList<? extends ItemRow> mTableDatas, TableParams leftParams, TableParams contentParams) {
        this.context = context;
        this.mTableDatas = mTableDatas;
        this.leftParams = leftParams;
        this.contentParams = contentParams;
        if (!mTableDatas.isEmpty()){
            sizeColumn = mTableDatas.get(0).size();
        }
    }

    @Override
    public ScrollViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.item_scrolltable_row, null);
        return new ScrollViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ScrollViewHolder holder, final int position) {

        ItemRow itemRow = mTableDatas.get(position);

        holder.text_TableLeft.setText(itemRow.get(0));
        int childCount = holder.layout_TableRow.getChildCount();
        for (int i = 0; i < childCount; i++) {
            FrameLayout itemLayout = (FrameLayout) holder.layout_TableRow.getChildAt(i);
            TextView text_item = (TextView) itemLayout.getChildAt(0);
            text_item.setText(itemRow.get(i + 1));
            text_item.setBackgroundColor(position == focusRow ? contentParams.getFoucsColor() : contentParams.getBackgroundColor());
        }

        mSynScrollerview.setOnScrollListener(new SynScrollerLayout.OnItemScrollView() {
            @Override
            public void OnScroll(int l, int t, int oldl, int oldt) {
                holder.synScroller_tableRow.smoothScrollTo(l, 0);
            }

        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mSynScrollerview.onTouchEvent(holder.layout_TableRow, position, event);
                return false;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ScrollViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int attachPosition = holder.getLayoutPosition();
        if (focusRow != -1 && attachPosition == focusRow){
            cacheLayout = holder.layout_TableRow;

            int childCount = cacheLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                FrameLayout itemLayout = (FrameLayout) cacheLayout.getChildAt(i);
                TextView childAt = (TextView) itemLayout.getChildAt(0);
                childAt.setBackgroundColor(contentParams.getFoucsColor());
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ScrollViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        int attachPosition = holder.getLayoutPosition();
        if (focusRow != -1 && attachPosition == focusRow){
            int childCount = cacheLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                FrameLayout itemLayout = (FrameLayout) cacheLayout.getChildAt(i);
                TextView childAt = (TextView) itemLayout.getChildAt(0);
                childAt.setBackgroundColor(contentParams.getBackgroundColor());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTableDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSynScrollerview(SynScrollerLayout mSynScrollerview) {
        this.mSynScrollerview = mSynScrollerview;
    }

    public void setFocusRow(LinearLayout focusView, int focusRow) {
        if (this.focusRow == focusRow){
            return;
        }

        if (cacheLayout != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (cacheLayout.isAttachedToWindow()){
                    int childCount = cacheLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        FrameLayout itemLayout = (FrameLayout) cacheLayout.getChildAt(i);
                        TextView childAt = (TextView) itemLayout.getChildAt(0);
                        childAt.setBackgroundColor(contentParams.getBackgroundColor());
                    }
                }
            }

            this.focusRow = focusRow;
            this.cacheLayout = focusView;

            int childCount = cacheLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                FrameLayout itemLayout = (FrameLayout) cacheLayout.getChildAt(i);
                TextView childAt = (TextView) itemLayout.getChildAt(0);
                childAt.setBackgroundColor(contentParams.getFoucsColor());
            }
        }
    }

    public int getFocusRow() {
        return focusRow;
    }

    protected class ScrollViewHolder extends RecyclerView.ViewHolder {

        public final TextView text_TableLeft;
        public final SynScrollerLayout synScroller_tableRow;
        public final LinearLayout layout_TableRow;

        //public final LinearLayout layout_FixedRow;

        public ScrollViewHolder(View itemView) {
            super(itemView);
            text_TableLeft = itemView.findViewById(R.id.text_TableLeft);
            synScroller_tableRow = itemView.findViewById(R.id.synScroller_tableRow);
            layout_TableRow = itemView.findViewById(R.id.layout_TableRow);

            //layout_FixedRow = itemView.findViewById(R.id.layout_FixedRow);
            freshLeftText();

            for (int column = 1; column < sizeColumn; column++) {
                View inflate = View.inflate(context, R.layout.item_tableview_text, null);
                TextView text_item = inflate.findViewById(R.id.text_Item_TableView);

                ViewGroup.LayoutParams layoutParams = text_item.getLayoutParams();
                layoutParams.height = contentParams.getHeight();
                layoutParams.width = contentParams.getWidth(column);
                text_item.setLayoutParams(layoutParams);

                text_item.setTextSize(contentParams.getTextSize());
                text_item.setTextColor(contentParams.getTextColor());
                text_item.setBackgroundColor(contentParams.getBackgroundColor());
                text_item.setPadding(contentParams.getPaddingLeft(), contentParams.getPaddingTop(), contentParams.getPaddingRight(), contentParams.getPaddingBottom());

                text_item.setGravity(contentParams.getItemGravity().getGravity());

                layout_TableRow.addView(inflate);
            }
        }

        private void freshLeftText() {
            ViewGroup.LayoutParams layoutParams = text_TableLeft.getLayoutParams();
            layoutParams.height = leftParams.getHeight();
            layoutParams.width = leftParams.getWidth(0);
            text_TableLeft.setLayoutParams(layoutParams);

            text_TableLeft.setTextSize(leftParams.getTextSize());
            text_TableLeft.setTextColor(leftParams.getTextColor());
            text_TableLeft.setBackgroundColor(leftParams.getBackgroundColor());
            text_TableLeft.setPadding(leftParams.getPaddingLeft(), leftParams.getPaddingTop(), leftParams.getPaddingRight(), leftParams.getPaddingBottom());
            text_TableLeft.setGravity(leftParams.getItemGravity().getGravity());
        }
    }
}
