package com.projekt.forum;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model){
        String var1 = "some value for testing";
        TestType var2 = new TestType("dude",3, "v2-xh23");
        model.addAttribute("atr_importantValue", var2);
        model.addAttribute("atr_unitsList", Arrays.asList(
                new PlayableUnit("Orc Savage",23),
                new PlayableUnit("Archmage", 69),
                new PlayableUnit("Elder Dragon", 120)
        ));
        model.addAttribute("atr_number",23);
        model.addAttribute("atr_link","https://pomodor.app/asafasef");
        return "Home";
    }

    private record TestType(String name, Integer id, String interStellarEngine ){};
    private record PlayableUnit (String name, Integer lvl){};
}
