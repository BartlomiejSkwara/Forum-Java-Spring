package com.projekt.forum.repositories;

import com.projekt.forum.entity.GrantedAuthorityEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends ListCrudRepository<GrantedAuthorityEntity,Integer> {
    @Override
    List<GrantedAuthorityEntity> findAll();
    @Override
    Optional<GrantedAuthorityEntity> findById(Integer integer);
}
