package com.projekt.forum.services;

import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.MessagePostForm;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MessageService {
    private final AlertManager alertManager;
    private final ThreadRepository threadRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;
    public MessageService(AlertManager alertManager, ThreadRepository threadRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.alertManager = alertManager;
        this.threadRepository = threadRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    //TODO potencjalna poprawka by tu sie przydała :< w celu usprawnienia wydajności ( tj. ilości zapytań do BD)
    @Transactional
    public boolean saveMessage(Integer threadId, MessagePostForm messagePostForm, String username){
        Optional<ThreadEntity> currentThread  = threadRepository.findEntityByIdThread(threadId);
        if (currentThread.isEmpty()){
            return false;
        }

        ///TODO tutaj w przyszłości po zmianach w security może byc potrzebna korekta
        Optional<UserProjection> userProjection = userRepository.findByUsername(username);
        if(userProjection.isEmpty()){
            return false;
        }
        UserEntity userEntity = entityManager.getReference(UserEntity.class, userProjection.get().getIduser());

        MessageEntity messageEntity = new MessageEntity(null, DateUtility.getCurrentDate(),messagePostForm.getContent(),userEntity,currentThread.get());
        messageRepository.save(messageEntity);
        currentThread.get().setMessageCount(currentThread.get().getMessageCount()+1);
        threadRepository.save(currentThread.get());


        return true;
    }
}