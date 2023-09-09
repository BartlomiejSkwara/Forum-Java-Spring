package com.projekt.forum.dataTypes.pageResponse;

import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import java.util.HashMap;
import java.util.List;

public class PageResponse<T>  {
    List<T> DTOList;
    private Integer currentPage;
    private Integer lastPage;
    private long totalResultsCount;
    private Integer maxOnPage;
    private Sort.Direction sortDirection;
    private String sortBy;
    private String filter;

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

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getFilter() {
        return filter;


    }

    public void setValues(Integer currentPage, Integer maxOnPage, long totalResultsCount){
        this.currentPage = currentPage;
        this.maxOnPage = maxOnPage;
        this.totalResultsCount = totalResultsCount;
        this.lastPage = (int) Math.ceil(((double) totalResultsCount/(double)maxOnPage))-1;

    }
    public void setSortingAndFilter(Sort.Direction sortDirection, String sortBy, String filter){
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
        this.filter = filter;
    }
}
