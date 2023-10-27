package com.projekt.forum.dataTypes.forms;

import com.projekt.forum.utility.ValidationUtility;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ThreadCUForm {
    @NotNull(message = "Nie przekazano żadnej nazwy wątku")
    //@NotBlank(message = "Pusta nazwa kategorii")
    @Size(  min = 1,
            max = 45,
            message = "Nazwa powinna mieć liczbę znaków w zakresie od 1 do 45")
    @Pattern(   regexp = ValidationUtility.URL_REGEX,
                message = "Wartość w polu \"Nazwa Wątku\" zawiera jeden z zakazanych znaków: \" \\' & < > \\ /")
    String threadTopic;


    @NotNull(message = "Nie przekazano pierwszej wiadomości")
    //@NotBlank(message = "Pusty opis kategorii")
    @Size(  min = 1,
            max = 180,
            message = "Pierwsza wiadomość powinna mieć liczbę znaków w zakresie od 1 do 180")
    @Pattern(   regexp = ValidationUtility.UNIVERSAL_REGEX,
                message = "Wartość w polu \" Pierwsza Wiadomość \" zawiera jeden z zakazanych znaków:  \" \' & < > ")
    String threadFirstMessage;

//    Integer threadID;

    public ThreadCUForm( String threadTopic, String threadFirstMessage){
        this.threadTopic = threadTopic;
        this.threadFirstMessage = threadFirstMessage;
        //this.threadID= categoryID;

    }


    public String getThreadTopic() {
        return threadTopic;
    }

    public String getThreadFirstMessage() {
        return threadFirstMessage;
    }
}
