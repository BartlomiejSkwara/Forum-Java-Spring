package com.projekt.forum.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity()
@Table(name = "user", schema = "forum")
public class UserEntity implements UserDetails {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return nonExpired == that.nonExpired && nonLocked == that.nonLocked && credentialsNonExpired == that.credentialsNonExpired && isEnabled == that.isEnabled && Objects.equals(iduser, that.iduser) && Objects.equals(authorities, that.authorities) && Objects.equals(password, that.password) && Objects.equals(username, that.username) && Objects.equals(creationDate, that.creationDate);
    }

    public UserEntity(){
    }

    public UserEntity(Integer id, Collection<GrantedAuthorityEntity> role, String password, String username, Date creationDate, boolean nonExpired, boolean nonLocked, boolean credentialsNonExpired, boolean isEnabled) {
        this.iduser = id;
        this.authorities = role;
        this.password = password;
        this.username = username;
        this.creationDate = creationDate;
        this.nonExpired = nonExpired;
        this.nonLocked = nonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iduser, authorities, password, username, creationDate, nonExpired, nonLocked, credentialsNonExpired, isEnabled);
    }

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userIDGen")
    @SequenceGenerator(name = "userIDGen", sequenceName = "userIDGen", initialValue = 34 )
    private Integer iduser;

    @OneToMany(mappedBy = "user")
    private Collection<GrantedAuthorityEntity> authorities;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;


    @Temporal(TemporalType.DATE)
    @Column(name = "creation_data")
    private Date creationDate;

    @Transient()
    private boolean nonExpired = true;
    @Transient()
    private boolean nonLocked = true;
    @Transient()
    private boolean credentialsNonExpired = true;
    @Transient()
    private boolean isEnabled = true;

    @Override
    public Collection<GrantedAuthorityEntity> getAuthorities() {
        return authorities;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public int getId(){return iduser;}
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return nonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setId(int id) {
        this.iduser = id;
    }


    public void setAuthorities(GrantedAuthorityEntity role) {
        getAuthorities().add(role);
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setNonExpired(boolean nonExpired) {
        this.nonExpired = nonExpired;
    }

    public void setNonLocked(boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
