package com.ycl.pagehelperlib.page.impl;

import com.ycl.pagehelperlib.page.IPage;

public abstract class Page1 extends IPage {

    @Override
    public int handleParams1(int pageIndex, int pageSize) {
        return ++pageIndex;
    }

    @Override
    public int handleParams2(int pageIndex, int pageSize) {
        return pageSize;
    }
}
