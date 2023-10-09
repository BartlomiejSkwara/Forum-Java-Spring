package com.projekt.forum.dataTypes.forms;

import com.projekt.forum.utility.ValidationUtility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MessagePostForm {


    @NotNull(message = "Próbujesz przekazać nieistniejąca wiadomość")
    @NotBlank(message = "Wiadomość nie może być pusta")
    @Size(
            max = 180,
            message = "Wiadomość może mieć maksymalnie 180 znaków")
    @Pattern(   regexp = ValidationUtility.UNIVERSAL_REGEX,
            message = "Wiadomość zawiera jeden z zakazanych znaków:  \" \' & < > ")
    String content;

    public MessagePostForm(String message) {
        this.content = message;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
