package com.ycl.pagehelperlib.adapter;

import com.ycl.pagehelperlib.LoadMoreState;

import java.util.List;

/**
 * 适配器
 *
 * @author ycl
 */
public interface IAdapter<T> {

    void setData(List<T> data);

    void appendData(List<T> data);

    void setLoadMoreState(LoadMoreState state);

}
