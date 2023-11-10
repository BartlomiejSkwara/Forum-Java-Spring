package com.projekt.forum.dataTypes.forms;

import com.projekt.forum.services.RegisterService;
import jakarta.validation.constraints.*;

public class RegisterForm {
    @NotNull(message = "Nie przekazano wartości")
    @Size(
    max = 20,
    min = 3,
    message = "'Login' powinien mieć od 3 do 20 znaków !!!"
    )
    private String login;

    @NotNull(message = "Nie wypełniono pola 'Email' !!!")
    @Email(message = "Podano niepoprawyn adres 'Email' !!!")
    @Size(max = 45, message = "'Email' nie powinien być dłuższy niż 45 znaków !!!")
    private String email;

    @NotNull(message = "Nie wypełniono pola 'Hasło' !!!")
    @Size(
            max = 45,
            min = 10,
            message = "Pole 'Hasło' powinno mieć od 10 do 45 znaków !!!"
    )
    private String password;


    public RegisterForm(String loginParam, String emailParam, String passwordParam){
        login=loginParam;
        email=emailParam;
        password=passwordParam;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
