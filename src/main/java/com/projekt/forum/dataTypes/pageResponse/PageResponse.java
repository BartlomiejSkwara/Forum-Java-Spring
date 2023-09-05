package com.projekt.forum.dataTypes.pageResponse;

import com.projekt.forum.dataTypes.ThreadDTO;

import java.util.List;

public class PageResponse<T>  {
    List<T> DTOList;
    private Integer currentPage;
    private Integer lastPage;
    private long totalResultsCount;
    private Integer maxOnPage;

    public List<T> getThreadDTOList() {
        return DTOList;
    }

    public void setThreadDTOList(List<T> threadDTOList) {
        this.DTOList = threadDTOList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public long getTotalResultsCount() {
        return totalResultsCount;
    }

    public Integer getMaxOnPage() {
        return maxOnPage;
    }

    public void setValues(Integer currentPage, Integer maxOnPage, long totalResultsCount){
        this.currentPage = currentPage;
        this.maxOnPage = maxOnPage;
        this.totalResultsCount = totalResultsCount;

        this.lastPage = (int)(Math.ceil((double)totalResultsCount/(double)maxOnPage));
    }
}
