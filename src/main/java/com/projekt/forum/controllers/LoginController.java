package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
public class LoginController {



    @GetMapping("/login")
    public String login(AlertManager alertManager, Model model, HttpServletRequest request){
        if(request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)!=null){
            String exception = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION).getClass().getSimpleName();
            if(exception.equals("UsernameNotFoundException")){
                alertManager.addAlert(new Alert("Podany użytkownik nie istnieje !!!", Alert.AlertType.DANGER));
            } else if (exception.equals("BadCredentialsException")) {
                alertManager.addAlert(new Alert("Podano niepoprawne dane !!!", Alert.AlertType.DANGER));
            }
            else {
                alertManager.addAlert(new Alert("ERROR", Alert.AlertType.DANGER));
            }
        }

        model.addAttribute("atr_alertManager", alertManager);
        return "Login";
    }

}
