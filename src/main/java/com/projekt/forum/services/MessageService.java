package com.projekt.forum.services;

import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.dto.MessageDTO;
import com.projekt.forum.dataTypes.forms.MessagePostForm;
import com.projekt.forum.dataTypes.pageResponse.PageResponse;
import com.projekt.forum.dataTypes.projection.UserProjection;
import com.projekt.forum.entity.MessageEntity;
import com.projekt.forum.entity.ThreadEntity;
import com.projekt.forum.entity.UserEntity;
import com.projekt.forum.repositories.MessageRepository;
import com.projekt.forum.repositories.ThreadRepository;
import com.projekt.forum.repositories.UserRepository;
import com.projekt.forum.utility.DateUtility;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MessageService {
    private final AlertManager alertManager;
    private final ThreadRepository threadRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final String sortProperty = "creationDate";
    public final Integer pageSize = 20;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    public MessageService(AlertManager alertManager, ThreadRepository threadRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.alertManager = alertManager;
        this.threadRepository = threadRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public PageResponse<MessageDTO> getMessagesByTopic(Integer threadId, Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.Direction.ASC,sortProperty);
        Page<MessageDTO> messageDTOPage = messageRepository.findByThreadEntity_IdThread_Pageable(threadId,pageable);

        PageResponse<MessageDTO> threadDTOPageResponse = new PageResponse<>();
        threadDTOPageResponse.setDTOList(messageDTOPage);
        threadDTOPageResponse.setValues(pageNumber,pageSize, messageDTOPage.getTotalElements());

        return threadDTOPageResponse;
    }

    //TODO potencjalna poprawka by tu sie przydała :< w celu usprawnienia wydajności ( tj. ilości zapytań do BD)
    @Transactional
    public boolean saveMessage(Integer threadId, MessagePostForm messagePostForm, String username, ThreadEntity currentThread){


        ///TODO tutaj w przyszłości po zmianach w security może byc potrzebna korekta
        Optional<UserProjection> userProjection = userRepository.findUserProjectionByUsername(username);
        if(userProjection.isEmpty()){
            return false;
        }
        UserEntity userEntity = entityManager.getReference(UserEntity.class, userProjection.get().getIduser());

        MessageEntity messageEntity = new MessageEntity(null, DateUtility.getCurrentDate(),messagePostForm.getContent(),userEntity,currentThread);
        messageRepository.save(messageEntity);
        currentThread.setMessageCount(currentThread.getMessageCount()+1);

        threadRepository.save(currentThread);


        return true;
    }



}
