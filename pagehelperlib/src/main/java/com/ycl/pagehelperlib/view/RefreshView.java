package com.ycl.pagehelperlib.view;

/**
 * 刷新控件
 */
public interface RefreshView {
    /**
     * 刷新
     */
    void setRefreshing(boolean isRefreshing);

    /**
     * 刷新状态
     */
    boolean isRefreshing();
}
