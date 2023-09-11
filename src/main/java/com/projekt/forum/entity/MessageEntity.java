package com.projekt.forum.entity;


import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;


@Entity
@Table(/*schema = "forum",*/ name = "message")
public class MessageEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmessage")
    Integer id;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date creationDate;

    @Column(name = "content")
    String content;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    UserEntity user;
    @ManyToOne()
    @JoinColumn(name = "thread_id")
    ThreadEntity threadEntity;

    public MessageEntity() {}

    public MessageEntity(Integer id, Date creationDate, String content, UserEntity user, ThreadEntity threadEntity) {
        this.id = id;
        this.creationDate = creationDate;
        this.content = content;
        this.user = user;
        this.threadEntity = threadEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUser() {
        return user;
    }



    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ThreadEntity getThreadEntity() {
        return threadEntity;
    }

    public void setThreadEntity(ThreadEntity threadEntity) {
        this.threadEntity = threadEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(creationDate, that.creationDate) && Objects.equals(content, that.content) && Objects.equals(user, that.user) && Objects.equals(threadEntity, that.threadEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, content, user, threadEntity);
    }
}
