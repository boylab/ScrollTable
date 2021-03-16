package com.boylab.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.boylab.example.bean.Student;
import com.boylab.example.bean.StudentLabel;
import com.boylab.scrolltable.TableView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Student> mTableDatas = new ArrayList<Student>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 查询所得数据
         */
        for (int i = 0; i < 30; i++) {
            mTableDatas.add(new Student(i));
        }

        TableView tableView = findViewById(R.id.tableView);
        /**
         * 可设置一些参数
         */
        tableView.setTableData(new StudentLabel(), mTableDatas);

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

                for (int i = 0; i < 30; i++) {
                    mTableDatas.add(new Student(i));
                }

                //刷新TableView即可
                mTableView.notifyDataSetChanged();

                //完成刷新
                mTableView.finishRefresh();

                if (mTableDatas.size() >= 30){
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

                int updateSize = (mTableDatas.size() < 100) ? 30 : 20;
                for (int i = 0; i < updateSize; i++) {
                    mTableDatas.add(new Student(i));
                }

                //刷新TableView即可
                mTableView.notifyDataSetChanged();

                //完成上拉加载
                mTableView.finishLoadMore();

                if (updateSize < 30){
                    //关闭上拉加载
                    mTableView.setEnableLoadMore(false);
                }
            }
        });

    }
}