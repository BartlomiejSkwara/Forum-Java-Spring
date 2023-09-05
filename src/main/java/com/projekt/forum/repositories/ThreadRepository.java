package com.projekt.forum.repositories;

import com.projekt.forum.dataTypes.ThreadDTO;
import com.projekt.forum.entity.ThreadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThreadRepository extends ListCrudRepository<ThreadEntity,Integer> {
//    List<ThreadEntity> findAllByCategoryUrl(@Param("CategoryUrl") String categoryUrl);
//    List<ThreadEntity> findAllByCategoryId(@Param("CategoryUrl") String categoryUrl);

    @Query("SELECT new com.projekt.forum.dataTypes.ThreadDTO(t.idThread,t.topic, t.creationDate,t.messageCount, t.updateDate, t.userID.username) FROM ThreadEntity t WHERE t.categoryId.url = :url")
    Page<ThreadDTO> findAllThreadsWithFilter(@Param("url") String categoryUrl, Pageable pageable);

//    t.idThread as idThread, t.topic as topic, t.creationDate as creationDate, t.messageCount as messageCount, t.updateDate as updateDate

}
