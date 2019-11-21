package com.ycl.pagehelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ycl.pagehelperlib.LoadMoreState;
import com.ycl.pagehelperlib.PageWrapper;
import com.ycl.pagehelperlib.adapter.IAdapter;
import com.ycl.pagehelperlib.page.impl.Page1;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IAdapter<String> {

    private PageWrapper<String> pageWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPage();
    }

    private void initPage() {
        Page1 page = new Page1() {
            @Override
            public void load(int param1, int param2) {

            }
        };
        pageWrapper = new PageWrapper<>(page,this);
    }

    @Override
    public void setData(List<String> data) {

    }

    @Override
    public void appendData(List<String> data) {

    }

    @Override
    public void setLoadMoreState(LoadMoreState state) {

    }
}
