package com.projekt.forum.dataTypes.dto;

import java.util.Date;
import java.util.Objects;

public class ThreadDTO {

    private Integer idThread;
    private String topic;
    private Date creationDate;
    private Integer messageCount;
    private Date updateDate;
    private String username;

    public ThreadDTO(Integer idThread, String topic, Date creationDate, Integer messageCount, Date updateDate, String username) {
        this.idThread = idThread;
        this.topic = topic;
        this.creationDate = creationDate;
        this.messageCount = messageCount;
        this.updateDate = updateDate;
        this.username = username;
    }

    public Integer getIdThread() {
        return idThread;
    }

    public String getTopic() {
        return topic;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThreadDTO threadDTO)) return false;
        return Objects.equals(idThread, threadDTO.idThread) && Objects.equals(topic, threadDTO.topic) && Objects.equals(creationDate, threadDTO.creationDate) && Objects.equals(messageCount, threadDTO.messageCount) && Objects.equals(updateDate, threadDTO.updateDate) && Objects.equals(username, threadDTO.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idThread, topic, creationDate, messageCount, updateDate, username);
    }

    @Override
    public String toString() {
        return "ThreadDTO{" +
                "idThread=" + idThread +
                ", topic='" + topic + '\'' +
                ", creationDate=" + creationDate +
                ", messageCount=" + messageCount +
                ", updateDate=" + updateDate +
                ", username='" + username + '\'' +
                '}';
    }
}
