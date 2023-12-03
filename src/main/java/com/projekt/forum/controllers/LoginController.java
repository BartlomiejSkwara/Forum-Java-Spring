package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.services.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    private final AlertManager alertManager;
    private final JWTService jwtService;
    @Autowired
    public LoginController(AlertManager alertManager, JWTService jwtService){
        this.alertManager = alertManager;
        this.jwtService = jwtService;
    }


    @RequestMapping("/loginSuccess")
    public String loginSuccess(HttpServletResponse httpServletResponse){
        alertManager.addAlert(new Alert("Zostałeś poprawnie zalogowany :>",Alert.AlertType.SUCCESS));
        SecurityContext securityContext = SecurityContextHolder.getContext();

        String jwtToken = jwtService.generateJWT((UserDetails) securityContext.getAuthentication().getPrincipal());
        jwtService.addTokenToResponse(httpServletResponse,jwtToken);


        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(){
        alertManager.addAlert(new Alert("Zostałeś wylogowany :>",Alert.AlertType.WARNING));
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
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

        //TODO przenieś to tam gdzie trzeba
//        else {
//            alertManager.addAlert(new Alert("Poprawnie zalogowano Witaj :>", Alert.AlertType.SUCCESS));
//
//        }
        model.addAttribute("atr_alertManager", alertManager);

        return "Login";
    }

}
