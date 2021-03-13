package com.boylab.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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
        for (int i = 0; i < 50; i++) {
            mTableDatas.add(new Student(i));
        }

        TableView tableView = findViewById(R.id.tableView);
        tableView.setTableData(new StudentLabel(), mTableDatas);

    }
}