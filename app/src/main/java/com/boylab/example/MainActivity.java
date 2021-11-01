package com.boylab.example;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.boylab.example.bean.Student;
import com.boylab.example.bean.StudentLabel;
import com.boylab.scrolltable.TableView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TableView tableView;

    private ArrayList<Student> mTableDatas = new ArrayList<Student>();
    private StudentLabel studentLabel = new StudentLabel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Student> mDatas = new ArrayList<Student>();
                for (int i = 0; i < 0; i++) {
                    mDatas.add(new Student(i));
                }
                loadFirst(mDatas);
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Student> mDatas = new ArrayList<Student>();
                for (int i = 0; i < 1; i++) {
                    mDatas.add(new Student(i));
                }
                loadFirst(mDatas);
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Student> mDatas = new ArrayList<Student>();
                for (int i = 0; i < 2; i++) {
                    mDatas.add(new Student(i));
                }
                loadFirst(mDatas);
            }
        });

        tableView = findViewById(R.id.tableView);
        //tableView.setEnableRefresh(true);
        tableView.setTableData(studentLabel, mTableDatas);

        /**
         * 点击监听
         */
        tableView.setmOnItemClickListenter(new TableView.OnItemClickListenter() {
            @Override
            public void onItemClick(View item, int position) {
                Toast.makeText(MainActivity.this, "onItemClick: "+position, Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 下拉刷新（用的少）
         */
        tableView.setOnRefreshListener(new TableView.OnTableRefreshListener() {
            @Override
            public void onRefresh(TableView mTableView) {
                mTableDatas.clear();

                for (int i = 0; i < 5; i++) {
                    mTableDatas.add(new Student(i));
                }

                //刷新TableView即可
                mTableView.notifyDataSetChanged();

                //完成刷新
                mTableView.finishRefresh();

                if (mTableDatas.size() >= 5){
                     //放开上拉加载
                    mTableView.setEnableLoadMore(true);
                }
            }
        });


        /**
         * 上拉加载
         */
        tableView.setOnLoadMoreListener(new TableView.OnTableLoadMoreListener() {
            @Override
            public void onLoadMore(TableView mTableView) {

                int updateSize = (mTableDatas.size() < 30) ? 5 : 3;
                for (int i = 0; i < updateSize; i++) {
                    mTableDatas.add(new Student(i));
                }

                //刷新TableView即可
                mTableView.notifyDataSetChanged();

                //完成上拉加载
                mTableView.finishLoadMore();

                if (updateSize < 5){
                    //关闭上拉加载
                    mTableView.setEnableLoadMore(false);
                }
            }
        });

    }

    private void loadFirst(ArrayList<Student> mDatas){
        mTableDatas.clear();
        mTableDatas.addAll(mDatas);
        tableView.notifyDataSetChanged();
    }
}