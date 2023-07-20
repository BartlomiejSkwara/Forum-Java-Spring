package com.projekt.forum;

import com.projekt.forum.dataTypes.Alert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("atr_alertList", Arrays.asList(
                new Alert("Warning bruuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuh", Alert.AlertType.WARNING),
                new Alert("success :>", Alert.AlertType.SUCCESS),
                new Alert("yep it is bad", Alert.AlertType.DANGER)

        ));

        return "Login";
    }

}
