package com.projekt.forum.repositories;

import com.projekt.forum.dataTypes.dto.ThreadDTO;
import com.projekt.forum.dataTypes.projection.ThreadProjection;
import com.projekt.forum.entity.ThreadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ThreadRepository extends ListCrudRepository<ThreadEntity,Integer> {
//    List<ThreadEntity> findAllByCategoryUrl(@Param("CategoryUrl") String categoryUrl);
//    List<ThreadEntity> findAllByCategoryId(@Param("CategoryUrl") String categoryUrl);


    @Query("SELECT new com.projekt.forum.dataTypes.dto.ThreadDTO(t.idThread,t.topic, t.creationDate,t.messageCount, t.updateDate, t.userID.username) FROM ThreadEntity t WHERE t.categoryId.url = :url AND ((:topicFilter IS null) OR (t.topic LIKE CONCAT('%', :topicFilter, '%')))")
    Page<ThreadDTO> findAllThreadsWithFilter(@Param("url") String categoryUrl, Pageable pageable, @Param("topicFilter") String topicFilter);

    @Query("SELECT t FROM ThreadEntity t WHERE t.idThread = :id")
    Optional<ThreadProjection> findProjectionByIdThread(@Param("id") Integer id);

    @Query("SELECT t FROM ThreadEntity t WHERE t.idThread = :id")
    Optional<ThreadEntity> findEntityByIdThread(@Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ThreadEntity t SET t.messageCount = :messageCount WHERE t.idThread = :id")
    void changeMessageCount(@Param("id") Integer threadId, @Param("messageCount") Integer messageCount);

//    t.idThread as idThread, t.topic as topic, t.creationDate as creationDate, t.messageCount as messageCount, t.updateDate as updateDate

}
