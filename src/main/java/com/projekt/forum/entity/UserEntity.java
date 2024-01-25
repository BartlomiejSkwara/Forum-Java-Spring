package com.projekt.forum.entity;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity()
@Table(name = "user_data"/*,schema = "forum"*/)
public class UserEntity  {

    public UserEntity(){
    }

    public UserEntity( GrantedAuthorityEntity role, String password, String username, String email, Date creationDate) {
        this.authority = role;
        this.password = password;
        this.username = username;
        this.email=email;
        this.creationDate = creationDate;
    }


    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.AUTO, generator = "userIDGen")
    //@SequenceGenerator(name = "userIDGen", sequenceName = "userIDGen", initialValue = 34 )
    private Integer iduser;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "roleID", nullable = false)
    private GrantedAuthorityEntity authority;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;


    @Temporal(TemporalType.DATE)
    @Column(name = "creation_data")
    private Date creationDate;

    public GrantedAuthorityEntity getAuthority() {
        return authority;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getId(){return iduser;}
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.iduser = id;
    }


    public void setAuthority(GrantedAuthorityEntity role) {
        this.authority = role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return Objects.equals(iduser, that.iduser) && Objects.equals(getAuthority(), that.getAuthority()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getCreationDate(), that.getCreationDate());
    }


    @Override
    public int hashCode() {
        return Objects.hash(iduser, authority, password, username, creationDate);
    }
}
