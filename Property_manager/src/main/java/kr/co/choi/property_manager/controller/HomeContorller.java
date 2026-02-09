package kr.co.choi.property_manager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeContorller {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message","Thymleaf OK");
        return "home";
    }


}
