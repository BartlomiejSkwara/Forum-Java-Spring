package com.projekt.forum.controllers;


import com.projekt.forum.dataTypes.AlertManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private final AlertManager alertManager;

    public ErrorController(AlertManager alertManager) {
        this.alertManager = alertManager;
    }

    @RequestMapping("/error")
    public String handleError(Model model){
        if (alertManager.containsDanger()){
            model.addAttribute("atr_alertManager",alertManager);
        }
        return "Error";
    }


}
