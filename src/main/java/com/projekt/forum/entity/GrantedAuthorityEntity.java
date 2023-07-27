package com.projekt.forum.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Objects;

@Entity
@Table(schema = "forum", name = "role")
public class GrantedAuthorityEntity implements GrantedAuthority {


    @Transient()
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @Id()
    @Column(name = "roleID")
    private Integer roleID;

    @Column(name = "name")
    private String role;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser")
    private UserEntity user;

    public GrantedAuthorityEntity(){}

    public GrantedAuthorityEntity(Integer roleID, String role, UserEntity user) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.roleID = roleID;
        this.role = role;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrantedAuthorityEntity that)) return false;
        return Objects.equals(roleID, that.roleID) && Objects.equals(role, that.role) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleID, role, user);
    }

    @Override
    public String getAuthority() {
        return this.role;
    }


    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}