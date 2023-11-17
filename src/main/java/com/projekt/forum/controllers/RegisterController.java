package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.RegisterForm;
import com.projekt.forum.services.RegisterService;
import com.projekt.forum.utility.RequestUtility;
import com.projekt.forum.utility.ValidationUtility;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final ValidationUtility validationUtility;
    private final AlertManager alertManager;
    private final RegisterService registerService;

    public RegisterController(ValidationUtility validationUtility, AlertManager alertManager, RegisterService registerService) {
        this.validationUtility = validationUtility;
        this.alertManager = alertManager;
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String registerView(){
        return "Register";
    }

    @PostMapping("/register")
    public String register(
            @Valid() @ModelAttribute() RegisterForm registerForm, BindingResult bindingResult,
            Model model, HttpServletResponse httpServletResponse
    ){


        if (validationUtility.ConvertValidationErrors(bindingResult,alertManager)){
            if (registerService.registerNewUser(registerForm)){
                RequestUtility.setupAjaxRedirectionHeaders(httpServletResponse,"/");
                alertManager.addAlert(new Alert("Rejestracja zakończona powodzeniem ! Witaj ".concat(registerForm.getLogin()), Alert.AlertType.SUCCESS));
            }
            else {
                RequestUtility.setupAjaxRedirectionHeaders(httpServletResponse,"/error");
                alertManager.addAlert(new Alert("Wystąpił krytyczny błąd podczas rejestracji :<", Alert.AlertType.DANGER));
            }

            return "Blank";

        }

        model.addAttribute("alerts",alertManager);
        model.addAttribute("clear",true);


        RequestUtility.setupAjaxInsertionHeaders(httpServletResponse);
        return "Components/alerts :: alertsList";
    }
}
