package com.lynch.model;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by JL on 2016/9/3/0003.
 */
public class Pagination {
    private int pageSize;
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @JSONField(name = "numberOfPages")
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    private int totalPages;
}
