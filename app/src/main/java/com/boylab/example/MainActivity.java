package com.boylab.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.boylab.scrolltable.TableView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableView tableView = findViewById(R.id.tableView);

        Log.i(">>>>>>", "onCreate: "+String.format("%6.0f", 6.0f));

    }
}