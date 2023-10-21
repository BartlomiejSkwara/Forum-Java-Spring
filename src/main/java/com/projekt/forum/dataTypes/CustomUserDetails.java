package com.projekt.forum.dataTypes;

import com.projekt.forum.entity.GrantedAuthorityEntity;
import com.projekt.forum.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    UserEntity userEntity;
    Collection<GrantedAuthorityEntity> authorityEntities;

    public CustomUserDetails(UserEntity userEntity){
        this.userEntity = userEntity;
        this.authorityEntities = new ArrayList<>();
        this.authorityEntities.add(userEntity.getAuthority());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityEntities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
