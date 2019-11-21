package com.ycl.pagehelperlib.page;

/**
 * 分页
 */
public abstract class IPage {
    /**
     * 默认起始页下标
     */
    private static final int DEFAULT_START_PAGE_INDEX = 0;
    /**
     * 默认分页大小
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 当前页下标
     */
    private int curPageIndex;

    /**
     * 开始页下标
     */
    private int startPageIndex = DEFAULT_START_PAGE_INDEX;
    /**
     * 记录上一次的页下标
     */
    private int lastPageIndex;
    /**
     * 分页大小
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 分页大小
     */
    private int pageCount;
    /**
     * 是否正在加载
     */
    private boolean isLoading;

    public boolean isLoading() {
        return isLoading;
    }

    /**
     * 锁
     */
    final Object lock = new Object();


    protected IPage() {
        initPageConfig();
    }

    /**
     * 初始化分页参数
     */
    private void initPageConfig() {
        curPageIndex = getStartPageIndex();
        lastPageIndex = curPageIndex;
        pageSize = getPageSize();
        isLoading = false;
    }

    /**
     * 加载分页数据
     * <p>
     * 分页策略1:[param1, param2] = [pageIndex, pageSize]
     * 分页策略2:[param1, param2] = [startIndex, endIndex]
     */
    public abstract void load(int param1, int param2);

    /**
     * 根据分页策略,处理第一个分页参数
     *
     * @param pageIndex
     * @param pageSize
     */
    public abstract int handleParams1(int pageIndex, int pageSize);


    /**
     * 根据分页策略,处理第er个分页参数
     *
     * @param pageIndex
     * @param pageSize
     */
    public abstract int handleParams2(int pageIndex, int pageSize);

    /**
     * 加载分页数据
     *
     * @param isFirstPage 是否是第一页
     */
    public void loadPage(boolean isFirstPage) {
        synchronized (lock) {
            if (isLoading) {
                return;
            } else {
                isLoading = true;
            }
        }
        if (isFirstPage) {
            //加载第一页数据
            curPageIndex = getStartPageIndex();
        } else {
            curPageIndex = handleParams1(curPageIndex, pageSize);
        }
        load(curPageIndex, handleParams2(curPageIndex, pageSize));
    }


    /**
     * 当前是否是第一页数据
     *
     * @return
     */
    public boolean isFirstPage() {
        return curPageIndex <= getStartPageIndex();
    }

    /**
     * 是否还有下一页数据
     *
     * @return
     */
    public boolean hasMore() {
        return curPageIndex < pageCount;
    }


    /**
     * 加载完成
     *
     * @param success 是否加载成功
     */
    public void finishLoad(boolean success) {
        synchronized (lock) {
            isLoading = false;
        }
        if (success) {
            lastPageIndex = curPageIndex;
        } else {
            curPageIndex = lastPageIndex;
        }
    }


    /**
     * 设置起始页下标
     */
    public IPage setStartPageIndex(int startPageIndex) {
        this.startPageIndex = startPageIndex;
        return this;
    }


    /**
     * 返回起始页下标
     */
    public int getStartPageIndex() {
        return startPageIndex;
    }


    public int getPageCount() {
        return pageCount;
    }

    public IPage setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    /**
     * 设置分页大小
     */
    public IPage setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 返回分页大小
     *
     * @return
     */
    public int getPageSize() {
        return DEFAULT_PAGE_SIZE;

    }
}