package com.boylab.scrolltable;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;

class SynScrollerLayout extends HorizontalScrollView {
    private ItemObserverable sObserverable = new ItemObserverable();
    private int mPosition = -1;
    private View mItemView;
    private OnItemClickListener mOnItemClickListener;

    private float mStartX;
    private float mStartY;

    public SynScrollerLayout(Context context) {
        super(context);
    }

    public SynScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SynScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (sObserverable != null) {
            sObserverable.notifiItemView(l, t, oldl, oldt);
        }
    }

    /**
     * 点击监听
     *
     * @param view
     * @param position
     * @param event
     */
    public synchronized void onTouchEvent(View view, int position, MotionEvent event) {
        if (mItemView == null || position == -1) {
            mItemView = view;
            mPosition = position;
            onTouchEvent(event);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                postDelayed(mMoveAction, 200);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                actionUp(event);
                mStartX = 0;
                mStartY = 0;
                break;
            default:
                break;
        }


        return false;
    }

    private void actionMove(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float dy = Math.abs(y - mStartY);
        float dx = Math.abs(x - mStartX);
        if (dx > 50 || dy > 0) {
            if (mItemView != null) {
                removeCallbacks(mMoveAction);
            }
        }
    }

    private void actionUp(MotionEvent event) {
        removeCallbacks(mMoveAction);
        float x = event.getX();
        float y = event.getY();
        float dy = Math.abs(y - mStartY);
        float dx = Math.abs(x - mStartX);
        if (dx < 50 && dy < 50) {
            if (mPosition != -1 && mItemView != null) {
                postDelayed(mUpAction, 50);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(mItemView, mPosition);
                }
            }
        } else {
            mPosition = -1;
            mItemView = null;
        }
    }

    Runnable mUpAction = new Runnable() {
        @Override
        public void run() {
            if (mItemView != null) {
                mPosition = -1;
                mItemView = null;
                removeCallbacks(mUpAction);
            }
        }
    };
    Runnable mMoveAction = new Runnable() {
        @Override
        public void run() {
            if (mItemView != null) {
            }
        }
    };

    /**
     * 点击监听
     *
     * @param onItemClickListener
     */

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    /**
     * 滚动监听
     *
     * @param listener
     */
    public void setOnScrollListener(OnItemScrollView listener) {
        if (sObserverable != null) {
            sObserverable.setListener(listener);
        }
    }

    public interface OnItemScrollView {
        void OnScroll(int l, int t, int oldl, int oldt);

    }

    class ItemObserverable {

        private final ArrayList<OnItemScrollView> mItemScrollViews;

        public ItemObserverable() {
            mItemScrollViews = new ArrayList<>();
        }

        private void setListener(OnItemScrollView listener) {
            mItemScrollViews.add(listener);
        }


        private void notifiItemView(int l, int t, int oldl, int oldt) {
            if (mItemScrollViews != null) {
                for (OnItemScrollView itemScrollView : mItemScrollViews) {
                    itemScrollView.OnScroll(l, t, oldl, oldt);
                }
            }
        }

    }

}
