package com.projekt.forum.repositories;

import com.projekt.forum.dataTypes.dto.MessageDTO;
import com.projekt.forum.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends ListCrudRepository<MessageEntity,Integer> {
//    List<MessageEntity> findByThreadEntity_IdThread(Integer idThread);

    @Query("SELECT new com.projekt.forum.dataTypes.dto.MessageDTO(m.id,m.creationDate,m.content,m.user.username) FROM MessageEntity m WHERE m.threadEntity.idThread = :threadID")
    List<MessageDTO> findByThreadEntity_IdThread(@Param("threadID") Integer idThread);

    @Query("SELECT new com.projekt.forum.dataTypes.dto.MessageDTO(m.id,m.creationDate,m.content,m.user.username) FROM MessageEntity m WHERE m.threadEntity.idThread = :threadID")
    Page<MessageDTO> findByThreadEntity_IdThread_Pageable(@Param("threadID") Integer idThread, Pageable pageable);
}
