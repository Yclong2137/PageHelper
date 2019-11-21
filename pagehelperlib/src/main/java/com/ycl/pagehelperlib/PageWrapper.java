package com.ycl.pagehelperlib;

import com.ycl.pagehelperlib.adapter.IAdapter;
import com.ycl.pagehelperlib.page.IPage;
import com.ycl.pagehelperlib.view.RefreshView;

import java.util.List;

public class PageWrapper<T> {

    private IPage page;

    private IAdapter<T> adapter;

    private RefreshView refreshView;


    public PageWrapper(IPage page, IAdapter<T> adapter) {
        if (page == null) {
            throw new NullPointerException("IPage加载器不能为空");
        }
        if (adapter == null) {
            throw new NullPointerException("IAdapter适配器不能为空");
        }
        this.adapter = adapter;
        initPage(page);
    }


    public PageWrapper(IPage page, IAdapter<T> adapter, RefreshView refreshView) {
        this(page, adapter);
        this.refreshView = refreshView;
    }

    private void initPage(IPage page) {
        this.page = page;
        this.page.setStartPageIndex(page.getStartPageIndex())
                .setPageSize(page.getPageSize());
    }


    public void setStartPageIndex(int startPageIndex) {
        this.page.setStartPageIndex(startPageIndex);
    }

    /**
     * 当前是否是第一页数据
     *
     * @return
     */
    public boolean isFirstPage() {
        return page.isFirstPage();
    }

    /**
     * 加载数据
     *
     * @param isFirstPage
     */
    public void loadPage(boolean isFirstPage) {
        if (isFirstPage && refreshView != null && !refreshView.isRefreshing()) {
            refreshView.setRefreshing(true);
        }
        page.loadPage(isFirstPage);
    }


    /**
     * 加载成功
     */
    public void success(List<T> data) {
        page.finishLoad(true);
        if (refreshView != null && refreshView.isRefreshing()) {
            refreshView.setRefreshing(false);
        }
        if (null != this.adapter) {
            if (isFirstPage()) {
                this.adapter.setData(data);
            } else {
                this.adapter.appendData(data);
            }
            if (page.hasMore()) {
                this.adapter.setLoadMoreState(LoadMoreState.COMPLETE);
            } else {
                this.adapter.setLoadMoreState(LoadMoreState.END);
            }
        }

    }

    /**
     * 加载失败
     */
    public void failure() {
        page.finishLoad(false);
        if (refreshView != null && refreshView.isRefreshing()) {
            refreshView.setRefreshing(false);
        }
        if (null != this.adapter) {
            this.adapter.setLoadMoreState(LoadMoreState.FAILURE);
        }
    }


    /**
     * 设置数据
     *
     * @param data
     */
    private void setNewData(List<T> data) {
        if (null == this.adapter) {
            return;
        }
        this.adapter.setData(data);
    }

    /**
     * 添加数据
     *
     * @param data
     */
    private void addData(List<T> data) {
        if (null == this.adapter) {
            return;
        }
        this.adapter.appendData(data);
    }


    /**
     * 是否加载中
     *
     * @return
     */
    public boolean isLoading() {
        return page.isLoading();
    }


}
