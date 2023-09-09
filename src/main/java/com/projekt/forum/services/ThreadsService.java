package com.projekt.forum.services;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.ThreadDTO;
import com.projekt.forum.dataTypes.forms.CategoryFilterForm;
import com.projekt.forum.dataTypes.pageResponse.PageResponse;
import com.projekt.forum.entity.ThreadEntity;
import com.projekt.forum.entity.UserEntity;
import com.projekt.forum.repositories.ThreadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class ThreadsService {
    private final ThreadRepository threadRepository;
    private final Integer pageSize = 10;
    private final String defaultSearchStrategy = "updateDate";
    private final AlertManager alertManager;

    public ThreadsService(ThreadRepository threadRepository, AlertManager alertManager) {
        this.threadRepository = threadRepository;
        this.alertManager = alertManager;
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

    public boolean deleteThread(Integer threadId, UserDetails user){
        if (threadId!=null)
        {

            Optional<ThreadEntity> threadEntity = threadRepository.findById(threadId);

            if(threadEntity.isPresent())
            {
                if (user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_admin"))
                        ||threadEntity.get().getUserID().getUsername().equals(user.getUsername()))
                {
                    threadRepository.delete(threadEntity.get());
                    alertManager.addAlert(new Alert("Poprawnie usunięto kategorię: "+threadEntity.get().getTopic(), Alert.AlertType.SUCCESS));
                    return true;
                }
                alertManager.addAlert(new Alert("Nie wolno usuwać cudzych wątków \uD83D\uDE21 !!! ", Alert.AlertType.DANGER));

            }else
            {
                alertManager.addAlert(new Alert("Usunięcie wątku nie powiodło się !!! ", Alert.AlertType.DANGER));
            }
        }
        else
        {
            alertManager.addAlert(new Alert("Nie sprecyzowano ID wątku do usunięcia !!!", Alert.AlertType.WARNING));
        }
        return false;
    }
}
