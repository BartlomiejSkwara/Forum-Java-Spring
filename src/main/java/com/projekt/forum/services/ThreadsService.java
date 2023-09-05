package com.projekt.forum.services;

import com.projekt.forum.dataTypes.ThreadDTO;
import com.projekt.forum.dataTypes.pageResponse.PageResponse;
import com.projekt.forum.repositories.ThreadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThreadsService {
    private final ThreadRepository threadRepository;
    private final Integer pageSize = 5;

    public ThreadsService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public PageResponse<ThreadDTO> getThreadsByCategory(String categoryUrl, Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<ThreadDTO> threadDTOPage = threadRepository.findAllThreadsWithFilter(categoryUrl, pageable);
        List<ThreadDTO> threadDTOList = threadDTOPage.getContent();

        PageResponse<ThreadDTO> threadsPageResponse = new PageResponse<>();
        threadsPageResponse.setThreadDTOList(threadDTOList);
        threadsPageResponse.setValues(pageNumber,pageSize,threadDTOPage.getTotalElements());

        return threadsPageResponse;
    }
}
