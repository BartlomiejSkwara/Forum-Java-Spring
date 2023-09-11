package com.projekt.forum.dataTypes.dto;

import java.util.Date;
import java.util.Objects;

public class MessageDTO {
    private int idMessage;
    private Date creationDate;
    private String content;
    private String authorName;

    public MessageDTO(int idMessage, Date creationDate, String content, String authorName) {
        this.idMessage = idMessage;
        this.creationDate = creationDate;
        this.content = content;
        this.authorName = authorName;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getContent() {
        return content;
    }

    public String getAuthorName() {
        return authorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageDTO that)) return false;
        return idMessage == that.idMessage && creationDate == that.creationDate && Objects.equals(content, that.content) && Objects.equals(authorName, that.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMessage, creationDate, content, authorName);
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "idMessage=" + idMessage +
                ", creationDate=" + creationDate +
                ", content='" + content + '\'' +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
