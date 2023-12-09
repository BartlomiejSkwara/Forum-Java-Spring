package com.projekt.forum.services;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.dto.ThreadDTO;
import com.projekt.forum.dataTypes.forms.CategoryFilterForm;
import com.projekt.forum.dataTypes.forms.ThreadCUForm;
import com.projekt.forum.dataTypes.pageResponse.PageResponse;
import com.projekt.forum.dataTypes.projection.CategoryProjection;
import com.projekt.forum.dataTypes.projection.UserProjection;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.entity.ThreadEntity;
import com.projekt.forum.entity.UserEntity;
import com.projekt.forum.repositories.CategoryRepository;
import com.projekt.forum.repositories.ThreadRepository;
import com.projekt.forum.repositories.UserRepository;
import com.projekt.forum.utility.DateUtility;
import com.projekt.forum.utility.RequestUtility;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class ThreadsService {
    private final ThreadRepository threadRepository;
    private final CategoryRepository categoryRepository;
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final Integer pageSize = 10;
    private final String defaultSearchStrategy = "updateDate";
    private final AlertManager alertManager;
    @PersistenceContext
    private EntityManager entityManager;

    public ThreadsService(ThreadRepository threadRepository, CategoryRepository categoryRepository, MessageService messageService, UserRepository userRepository, AlertManager alertManager) {
        this.threadRepository = threadRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.messageService = messageService;
        this.alertManager = alertManager;
    }

    public PageResponse<ThreadDTO> getThreadsByCategory(String categoryUrl, Integer pageNumber, Sort.Direction sortDirection, String sortProperty ,String filter){
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sortDirection, sortProperty);
        Page<ThreadDTO> threadDTOPage = threadRepository.findAllThreadsWithFilter(categoryUrl, pageable,filter);

        PageResponse<ThreadDTO> threadsPageResponse = new PageResponse<>();
        threadsPageResponse.setDTOList(threadDTOPage);
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

    @Transactional
    public Pair<Boolean,Integer> createThread(String categoryURL, String username, ThreadCUForm threadCUForm) {
        Date date = DateUtility.getCurrentDateWithoutZoneOffset();

        Optional<CategoryProjection> categoryProjection = categoryRepository.findCategoryProjectionByUrl(categoryURL);
        if(categoryProjection.isEmpty()){
            return new Pair<>(false,null);
        }
        CategoryEntity categoryEntity = entityManager.getReference(CategoryEntity.class,categoryProjection.get().getCategoryID());


        Optional<UserProjection> userProjection = userRepository.findUserProjectionByUsername(username);
        if(userProjection.isEmpty()){
            return new Pair<>(false,null);
        }
        UserEntity userEntity = entityManager.getReference(UserEntity.class, userProjection.get().getIduser());



        ThreadEntity threadEntity = new ThreadEntity(threadCUForm.getThreadTopic(),date,categoryEntity,userEntity);
        threadRepository.save(threadEntity);
        messageService.saveMessage(threadEntity.getIdThread(),threadCUForm.getThreadFirstMessage(),userEntity,threadEntity);



        return new Pair<>(true,threadEntity.getIdThread());
    }
}
