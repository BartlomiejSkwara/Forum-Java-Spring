package com.projekt.forum.dataTypes;

import java.util.Objects;

public class Alert {
    private String text;
    private AlertType type;

    public Alert(String text, AlertType type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public AlertType getType() {
        return type;
    }

    public String getTypeName(){

        return switch (this.type){
            case DANGER ->  "danger";
            case SUCCESS -> "success";
            case WARNING -> "warning";
        };


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return Objects.equals(text, alert.text) && type == alert.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, type);
    }


    public enum AlertType{
        SUCCESS,
        WARNING,
        DANGER
    }
}
