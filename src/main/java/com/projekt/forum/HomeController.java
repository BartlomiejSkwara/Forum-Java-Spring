package com.projekt.forum;

import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @Autowired  CategoryRepository categoryRepository;

    @ResponseBody
    @GetMapping("/list")
    public List<CategoryEntity> listThemAll (){

        return categoryRepository.findAll();
    }
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

        model.addAttribute("categories",categoryRepository.findAll());
        return "Home";
    }

    private record TestType(String name, Integer id, String interStellarEngine ){};
    private record PlayableUnit (String name, Integer lvl){};
}
