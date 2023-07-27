package com.projekt.forum.repositories;

import com.projekt.forum.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends ListCrudRepository<UserEntity,Integer> {
    @Override
    List<UserEntity> findAll();

    @Query("SELECT u FROM UserEntity u  LEFT JOIN FETCH u.authorities")
    @Transactional()
    List<UserEntity> joinUsersWithRole();
}
