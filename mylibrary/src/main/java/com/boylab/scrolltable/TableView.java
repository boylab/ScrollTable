package com.boylab.scrolltable;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boylab.protocol.ItemGravity;
import com.boylab.protocol.ItemRow;
import com.boylab.utils.DensityUtil;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TableView extends LinearLayout {

    /**
     * 头部视图
     */
    private LinearLayout layout_HeadView, layout_headRow;
    private TextView text_Heading;
    private SynScrollerLayout scrollerview_Head;

    /**
     * table视图
     */
    private SmartRefreshLayout refreshLayout;
    private RecyclerView rv_TableView;
    private TableViewAdapter tableViewAdapter;

    /**
     * 参数
     */
    private HashMap<Integer, Integer> itemWidths = new HashMap<>();
    private float itemHeight = TableParams.HEIGHT;
    private int divider = getContext().getResources().getColor(android.R.color.darker_gray);
    private TableParams headParams, leftParams, contentParams;

    /**
     * 数据结构
     */
    private ItemRow headRow;
    private ArrayList<? extends ItemRow> mTableDatas = new ArrayList<ItemRow>();

    /**
     *  点击
     * 下拉刷新、上拉加载监听
     */
    private OnItemClickListenter mOnItemClickListenter;
    private OnTableRefreshListener mRefreshListener;
    private OnTableLoadMoreListener mLoadMoreListener;

    public TableView(Context context) {
        this(context, null, 0);
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseRes(context, attrs);
        initView(context);
    }

    private void parseRes(Context context, AttributeSet attrs) {
        DensityUtil.initDensity(context);

        headParams = new TableParams(getResources().getColor(android.R.color.holo_blue_light));
        leftParams = new TableParams(getResources().getColor(android.R.color.holo_blue_bright));
        contentParams = new TableParams(getResources().getColor(android.R.color.white));
        contentParams.setFoucsColor(getResources().getColor(android.R.color.holo_blue_bright));
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TableView);

            int resourceId = typedArray.getResourceId(R.styleable.TableView_rowWidths, 0);
            if (resourceId != 0){
                int[] intArray = getResources().getIntArray(resourceId);
                if (intArray != null) {
                    for (int i = 0; i < intArray.length; i++) {
                        itemWidths.put(i, intArray[i]);
                    }
                }
            }

            itemHeight = typedArray.getDimension(R.styleable.TableView_rowHeight, TableParams.HEIGHT);
            headParams.setHeight((int) itemHeight);
            headParams.setItemWidth(itemWidths);
            leftParams.setHeight((int) itemHeight);
            leftParams.setItemWidth(itemWidths);
            contentParams.setHeight((int) itemHeight);
            contentParams.setItemWidth(itemWidths);

            divider = typedArray.getColor(R.styleable.TableView_rowDivider, getResources().getColor(android.R.color.darker_gray));
            int foucsColor = typedArray.getColor(R.styleable.TableView_rowFoucsColor, getResources().getColor(android.R.color.holo_blue_bright));
            headParams.setFoucsColor(foucsColor);
            leftParams.setFoucsColor(foucsColor);
            contentParams.setFoucsColor(foucsColor);

            /**
             * 标头部参数
             */
            float headTextSize = typedArray.getDimension(R.styleable.TableView_headTextSize, TableParams.TEXT_SIZE);
            int headTextColor = typedArray.getColor(R.styleable.TableView_headTextColor, getResources().getColor(android.R.color.black));
            int headBackgroundColor = typedArray.getColor(R.styleable.TableView_headBackgroundColor, getResources().getColor(android.R.color.holo_blue_light));
            float headPaddingTop = typedArray.getDimension(R.styleable.TableView_headPaddingTop, TableParams.PADDING);
            float headPaddingLeft = typedArray.getDimension(R.styleable.TableView_headPaddingLeft, TableParams.PADDING);
            float headPaddingBottom = typedArray.getDimension(R.styleable.TableView_headPaddingBottom, TableParams.PADDING);
            float headPaddingRight = typedArray.getDimension(R.styleable.TableView_headPaddingRight, TableParams.PADDING);
            ItemGravity headGravity = ItemGravity.fromId(typedArray.getInt(R.styleable.TableView_headGravity, ItemGravity.CENTER.ordinal()));

            headParams.setTextSize(headTextSize);
            headParams.setTextColor(headTextColor);
            headParams.setBackgroundColor(headBackgroundColor);
            headParams.setPaddingTop((int) headPaddingTop);
            headParams.setPaddingLeft((int) headPaddingLeft);
            headParams.setPaddingBottom((int) headPaddingBottom);
            headParams.setPaddingRight((int) headPaddingRight);
            headParams.setItemGravity(headGravity);

            /**
             * 表固定列参数
             */
            float leftTextSize = typedArray.getDimension(R.styleable.TableView_leftTextSize, TableParams.TEXT_SIZE);
            int leftTextColor = typedArray.getColor(R.styleable.TableView_leftTextColor, getResources().getColor(android.R.color.black));
            int leftBackgroundColor = typedArray.getColor(R.styleable.TableView_leftBackgroundColor, getResources().getColor(android.R.color.holo_blue_bright));
            float leftPaddingTop = typedArray.getDimension(R.styleable.TableView_leftPaddingTop, TableParams.PADDING);
            float leftPaddingLeft = typedArray.getDimension(R.styleable.TableView_leftPaddingLeft, TableParams.PADDING);
            float leftPaddingBottom = typedArray.getDimension(R.styleable.TableView_leftPaddingBottom, TableParams.PADDING);
            float leftPaddingRight = typedArray.getDimension(R.styleable.TableView_leftPaddingRight, TableParams.PADDING);
            ItemGravity leftGravity = ItemGravity.fromId(typedArray.getInt(R.styleable.TableView_leftGravity, ItemGravity.CENTER.ordinal()));

            leftParams.setTextSize(leftTextSize);
            leftParams.setTextColor(leftTextColor);
            leftParams.setBackgroundColor(leftBackgroundColor);
            leftParams.setPaddingTop((int) leftPaddingTop);
            leftParams.setPaddingLeft((int) leftPaddingLeft);
            leftParams.setPaddingBottom((int) leftPaddingBottom);
            leftParams.setPaddingRight((int) leftPaddingRight);
            leftParams.setItemGravity(leftGravity);

            /**
             * 表内容参数
             */
            float contentTextSize = typedArray.getDimension(R.styleable.TableView_contentTextSize, TableParams.TEXT_SIZE);
            int contentTextColor = typedArray.getColor(R.styleable.TableView_contentTextColor, getResources().getColor(android.R.color.black));
            int contentBackgroundColor = typedArray.getColor(R.styleable.TableView_contentBackgroundColor, getResources().getColor(android.R.color.white));
            float contentPaddingTop = typedArray.getDimension(R.styleable.TableView_contentPaddingTop, TableParams.PADDING);
            float contentPaddingLeft = typedArray.getDimension(R.styleable.TableView_contentPaddingLeft, TableParams.PADDING);
            float contentPaddingBottom = typedArray.getDimension(R.styleable.TableView_contentPaddingBottom, TableParams.PADDING);
            float contentPaddingRight = typedArray.getDimension(R.styleable.TableView_contentPaddingRight, TableParams.PADDING);
            ItemGravity contentGravity = ItemGravity.fromId(typedArray.getInt(R.styleable.TableView_contentGravity, ItemGravity.CENTER.ordinal()));

            contentParams.setTextSize(contentTextSize);
            contentParams.setTextColor(contentTextColor);
            contentParams.setBackgroundColor(contentBackgroundColor);
            contentParams.setPaddingTop((int) contentPaddingTop);
            contentParams.setPaddingLeft((int) contentPaddingLeft);
            contentParams.setPaddingBottom((int) contentPaddingBottom);
            contentParams.setPaddingRight((int) contentPaddingRight);
            contentParams.setItemGravity(contentGravity);

            typedArray.recycle();
        }
    }

    private void initView(Context context) {
        View rootView = View.inflate(context, R.layout.layout_tableview, this);

        layout_HeadView = findViewById(R.id.layout_HeadView);
        layout_headRow = findViewById(R.id.layout_headRow);
        layout_HeadView.setClickable(true);
        text_Heading = findViewById(R.id.text_Heading);
        scrollerview_Head = findViewById(R.id.scrollerview_Head);

        refreshLayout = findViewById(R.id.refreshLayout);
        rv_TableView = findViewById(R.id.rv_TableView);

        setDivider(divider);
        freshHeadRow();

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_TableView.setLayoutManager(layoutManager);

        layout_HeadView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollerview_Head.onTouchEvent(motionEvent);
                return false;
            }
        });

        rv_TableView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollerview_Head.onTouchEvent(motionEvent);
                return false;
            }
        });

        scrollerview_Head.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                tableViewAdapter.setFocusRow((LinearLayout) view, position);
                if (mOnItemClickListenter != null){
                    mOnItemClickListenter.onItemClick(view, position);
                }
            }
        });
    }

    private void freshHeadRow() {
        //左上角TextView
        ViewGroup.LayoutParams layoutParams = text_Heading.getLayoutParams();
        layoutParams.height = headParams.getHeight();
        layoutParams.width = headParams.getWidth(0);
        text_Heading.setLayoutParams(layoutParams);

        text_Heading.setTextSize(headParams.getTextSize());
        text_Heading.setTextColor(headParams.getTextColor());
        text_Heading.setBackgroundColor(headParams.getBackgroundColor());
        text_Heading.setPadding(headParams.getPaddingLeft(), headParams.getPaddingTop(), headParams.getPaddingRight(), headParams.getPaddingBottom());
        text_Heading.setGravity(headParams.getItemGravity().getGravity());

        //HeadRow
        if (headRow != null){
            layout_headRow.removeAllViews();
            for (int column = 1; column < headRow.size(); column++) {
                View inflateText = inflateText(column);
                layout_headRow.addView(inflateText);
            }
        }

    }

    /**
     * 注意：headParams相关参数须提前设置
     *
     * @param headRow
     */
    public void setTableData(ItemRow headRow, ArrayList<? extends ItemRow> mTableDatas) {
        this.headRow = headRow;
        freshHeadRow();
        setTableDatas(mTableDatas);
    }

    private View inflateText(int column) {
        View inflate = View.inflate(getContext(), R.layout.item_tableview_text, null);
        TextView text_item = inflate.findViewById(R.id.text_Item_TableView);

        ViewGroup.LayoutParams layoutParams = text_item.getLayoutParams();
        layoutParams.height = headParams.getHeight();
        layoutParams.width = headParams.getWidth(column);
        text_item.setLayoutParams(layoutParams);

        text_item.setTextSize(headParams.getTextSize());
        text_item.setTextColor(headParams.getTextColor());
        text_item.setBackgroundColor(headParams.getBackgroundColor());
        text_item.setPadding(headParams.getPaddingLeft(), headParams.getPaddingTop(), headParams.getPaddingRight(), headParams.getPaddingBottom());

        text_item.setGravity(headParams.getItemGravity().getGravity());

        text_item.setText(headRow.get(column));
        return inflate;
    }

    /**
     * 注意：leftParams, contentParams相关参数须提前设置
     *
     * @param mTableDatas
     */
    private void setTableDatas(ArrayList<? extends ItemRow> mTableDatas) {
        this.mTableDatas = mTableDatas;

        tableViewAdapter = new TableViewAdapter(getContext(), this.mTableDatas, leftParams, contentParams);
        tableViewAdapter.setSynScrollerview(scrollerview_Head);
        rv_TableView.setAdapter(tableViewAdapter);
    }

    public void notifyDataSetChanged() {
        freshHeadRow();
        if (tableViewAdapter != null){
            tableViewAdapter.notifyDataSetChanged();
        }
    }

    public void setmOnItemClickListenter(OnItemClickListenter mOnItemClickListenter) {
        this.mOnItemClickListenter = mOnItemClickListenter;
    }

    public void setEnableRefresh(boolean enabled) {
        refreshLayout.setEnableRefresh(enabled);
    }

    public void setEnableLoadMore(boolean enabled) {
        refreshLayout.setEnableLoadMore(enabled);
    }

    public void finishRefresh() {
        refreshLayout.finishRefresh(true);
    }

    public void finishLoadMore() {
        refreshLayout.finishLoadMore(true);
    }

    public void setOnRefreshListener(OnTableRefreshListener onRefreshListener) {
        this.mRefreshListener = onRefreshListener;
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                tableViewAdapter.setFocusRow(null, -1);
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh(TableView.this);
                }
            }
        });
        //调用 finishRefresh(boolean success);
    }

    public void setOnLoadMoreListener(OnTableLoadMoreListener onLoadMoreListener) {
        this.mLoadMoreListener = onLoadMoreListener;
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.onLoadMore(TableView.this);
                }
            }
        });
        //调用 finishRefresh(boolean success);
    }

    public int getDivider() {
        return divider;
    }

    public void setDivider(int divider) {
        this.divider = divider;
        layout_HeadView.setBackgroundColor(divider);
        rv_TableView.setBackgroundColor(divider);
    }

    /**
     * Freshed HeadRow
     * @param itemHeight
     * @param itemWidths
     */
    public void setItemRect(int itemHeight, HashMap<Integer, Integer> itemWidths) {
        setItemHeight(itemHeight);
        setItemWidths(itemWidths);

        freshHeadRow();
    }

    public int getItemHeight() {
        return (int) itemHeight;
    }

    public void setItemHeight(float itemHeight) {
        this.itemHeight = itemHeight;
        headParams.setHeight((int) this.itemHeight);
        leftParams.setHeight((int) this.itemHeight);
        contentParams.setHeight((int) this.itemHeight);
    }

    public HashMap<Integer, Integer> getItemWidths() {
        return itemWidths;
    }

    public void setItemWidths(HashMap<Integer, Integer> itemWidths) {
        if (itemWidths == null){
            return;
        }

        if (itemWidths.isEmpty()){
            this.itemWidths.clear();
        }else {
            Set<Integer> keySet = itemWidths.keySet();
            for(int key : keySet){
                this.itemWidths.put(key, itemWidths.get(key));
            }
            headParams.setItemWidth(this.itemWidths);
            leftParams.setItemWidth(this.itemWidths);
            contentParams.setItemWidth(this.itemWidths);

            // freshHeading();
        }
    }

    public int itemWidth(int column) {
        if (itemWidths.containsKey(column)){
            return itemWidths.get(column);
        }
        return TableParams.HEIGHT;
    }

    /**
     * 参数
     * headParams,
     * leftParams,
     * contentParams;
     */
    public float getHeadTextSize() {
        return headParams.getTextSize();
    }

    public void setHeadTextSize(int headTextSize) {
        headParams.setTextSize(headTextSize);
    }

    public int getHeadTextColor() {
        return headParams.getTextColor();
    }

    public void setHeadTextColor(int headTextColor) {
        headParams.setTextColor(headTextColor);
    }

    public int getHeadBackgroundColor() {
        return headParams.getBackgroundColor();
    }

    public void setHeadBackgroundColor(int headBackgroundColor) {
        headParams.setBackgroundColor(headBackgroundColor);
    }

    public int getHeadPaddingTop() {
        return headParams.getPaddingTop();
    }

    public int getHeadPaddingLeft() {
        return headParams.getPaddingLeft();
    }

    public int getHeadPaddingBottom() {
        return headParams.getPaddingBottom();
    }

    public int getHeadPaddingRight() {
        return headParams.getPaddingRight();
    }

    public void setHeadPadding(int top, int left, int bottom, int right) {
        headParams.setPaddingTop(top);
        headParams.setPaddingLeft(left);
        headParams.setPaddingBottom(bottom);
        headParams.setPaddingRight(right);
    }

    public ItemGravity getHeadGravity() {
        return headParams.getItemGravity();
    }

    public void setHeadGravity(ItemGravity headGravity) {
        headParams.setItemGravity(headGravity);
    }

    public float getLeftTextSize() {
        return leftParams.getTextSize();
    }

    public void setLeftTextSize(int leftTextSize) {
        leftParams.setTextSize(leftTextSize);
    }

    public int getLeftTextColor() {
        return leftParams.getTextColor();
    }

    public void setLeftTextColor(int leftTextColor) {
        leftParams.setTextColor(leftTextColor);
    }

    public int getLeftBackgroundColor() {
        return leftParams.getBackgroundColor();
    }

    public void setLeftBackgroundColor(int leftBackgroundColor) {
        leftParams.setBackgroundColor(leftBackgroundColor);
    }

    public int getLeftPaddingTop() {
        return leftParams.getPaddingTop();
    }

    public int getLeftPaddingLeft() {
        return leftParams.getPaddingLeft();
    }

    public int getLeftPaddingBottom() {
        return leftParams.getPaddingBottom();
    }

    public int getLeftPaddingRight() {
        return leftParams.getPaddingRight();
    }

    public void setLeftPadding(int top, int left, int bottom, int right) {
        leftParams.setPaddingTop(top);
        leftParams.setPaddingLeft(left);
        leftParams.setPaddingBottom(bottom);
        leftParams.setPaddingRight(right);
    }

    public ItemGravity getLeftGravity() {
        return leftParams.getItemGravity();
    }

    public void setLeftGravity(ItemGravity leftGravity) {
        leftParams.setItemGravity(leftGravity);
    }

    public float getContentTextSize() {
        return contentParams.getTextSize();
    }

    public void setContentTextSize(int contentTextSize) {
        contentParams.setTextSize(contentTextSize);
    }

    public int getContentTextColor() {
        return contentParams.getTextColor();
    }

    public void setContentTextColor(int contentTextColor) {
        contentParams.setTextColor(contentTextColor);

    }

    public int getContentBackgroundColor() {
        return contentParams.getBackgroundColor();
    }

    public void setContentBackgroundColor(int contentBackgroundColor) {
        contentParams.setBackgroundColor(contentBackgroundColor);
    }

    public int getContentPaddingTop() {
        return contentParams.getPaddingTop();
    }

    public int getContentPaddingLeft() {
        return contentParams.getPaddingLeft();
    }

    public int getContentPaddingBottom() {
        return contentParams.getPaddingBottom();
    }

    public int getContentPaddingRight() {
        return contentParams.getPaddingRight();
    }

    public void setContentPadding(int top, int left, int bottom, int right) {
        contentParams.setPaddingTop(top);
        contentParams.setPaddingLeft(left);
        contentParams.setPaddingBottom(bottom);
        contentParams.setPaddingRight(right);
    }

    public ItemGravity getContentGravity() {
        return contentParams.getItemGravity();
    }

    public void setContentGravity(ItemGravity contentGravity) {
        contentParams.setItemGravity(contentGravity);
    }

    /**
     * 下拉刷新监听
     */
    public interface OnTableRefreshListener {

        void onRefresh(TableView mTableView);
    }

    /**
     * 上拉加载监听
     */
    public interface OnTableLoadMoreListener {

        void onLoadMore(TableView mTableView);
    }

    /**
     * 说明 Item点击事件
     * 作者 郭翰林
     * 创建时间 2018/2/2 下午4:50
     */
    public interface OnItemClickListenter {

        /**
         * @param item     点击项
         * @param position 点击位置
         */
        void onItemClick(View item, int position);
    }

}
