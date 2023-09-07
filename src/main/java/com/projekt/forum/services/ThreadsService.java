package com.projekt.forum.services;

import com.projekt.forum.dataTypes.ThreadDTO;
import com.projekt.forum.dataTypes.forms.CategoryFilterForm;
import com.projekt.forum.dataTypes.pageResponse.PageResponse;
import com.projekt.forum.repositories.ThreadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThreadsService {
    private final ThreadRepository threadRepository;
    private final Integer pageSize = 10;
    private final String defaultSearchStrategy = "updateDate";

    public ThreadsService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public PageResponse<ThreadDTO> getThreadsByCategory(String categoryUrl, Integer pageNumber, Sort.Direction sortDirection, String sortProperty ,String filter){
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sortDirection, sortProperty);
        Page<ThreadDTO> threadDTOPage = threadRepository.findAllThreadsWithFilter(categoryUrl, pageable,filter);
        List<ThreadDTO> threadDTOList = threadDTOPage.getContent();

        PageResponse<ThreadDTO> threadsPageResponse = new PageResponse<>();
        threadsPageResponse.setThreadDTOList(threadDTOList);
        threadsPageResponse.setValues(pageNumber,pageSize,threadDTOPage.getTotalElements());
        threadsPageResponse.setSortingAndFilter(sortDirection,sortProperty,filter);

        return threadsPageResponse;
    }
    public PageResponse<ThreadDTO> getThreadsByCategory(String categoryUrl, Integer pageNumber) {
        return getThreadsByCategory(categoryUrl,pageNumber, Sort.Direction.DESC, defaultSearchStrategy,null);
    }
    public PageResponse<ThreadDTO> getThreadsByCategory(String categoryUrl, CategoryFilterForm categoryFilterForm) {
        return getThreadsByCategory(categoryUrl,categoryFilterForm.getCurrentPage(), categoryFilterForm.getSortDirection(), categoryFilterForm.getSortProperty(),categoryFilterForm.getFilter());
    }

}
