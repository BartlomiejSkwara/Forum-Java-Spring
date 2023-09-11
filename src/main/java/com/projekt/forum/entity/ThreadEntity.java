package com.projekt.forum.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(/*schema = "forum",*/ name = "thread")
public class ThreadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idthread")
    Integer idThread;
    @Column(name="topic", length = 45)
    String topic;
    @Column(name="creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date creationDate;
    @Column(name="update_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date updateDate;
    @ManyToOne()
    @JoinColumn(name="category_id")
    CategoryEntity categoryId;
    @Column(name="message_count")
    Integer messageCount;
    @ManyToOne()
    @JoinColumn(name="user_id")
    UserEntity userID;

    public ThreadEntity(){}

    public ThreadEntity(String topic, Date creationDate, Date updateDate, CategoryEntity categoryId, Integer messageCount, UserEntity userID) {
        this.topic = topic;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.categoryId = categoryId;
        this.messageCount = messageCount;
        this.userID = userID;
    }

    public Integer getIdThread() {
        return idThread;
    }

    public void setIdThread(Integer idThread) {
        this.idThread = idThread;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public CategoryEntity getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryEntity categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public UserEntity getUserID() {
        return userID;
    }

    public void setUserID(UserEntity userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThreadEntity that)) return false;
        return Objects.equals(idThread, that.idThread) && Objects.equals(topic, that.topic) && Objects.equals(creationDate, that.creationDate) && Objects.equals(updateDate, that.updateDate) && Objects.equals(categoryId, that.categoryId) && Objects.equals(messageCount, that.messageCount) && Objects.equals(userID, that.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idThread, topic, creationDate, updateDate, categoryId, messageCount, userID);
    }
}
