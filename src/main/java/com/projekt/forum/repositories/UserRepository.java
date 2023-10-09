package com.projekt.forum.repositories;

import com.projekt.forum.dataTypes.projection.UserProjection;
import com.projekt.forum.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ListCrudRepository<UserEntity,Integer> {
    @Override
    List<UserEntity> findAll();

    @Query("SELECT u FROM UserEntity u  LEFT JOIN FETCH u.authority")
    List<UserEntity> joinUsersWithRole();

    Optional<UserProjection> findByUsername(String username);
}
