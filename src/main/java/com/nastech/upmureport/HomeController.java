package com.nastech.upmureport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;

@Controller
public class HomeController {
	
    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("_TEST", Calendar.getInstance().getTime());
        return "index";
    }

    @GetMapping(value = "/temp")
    public String temp() {
        return "template";
    }
}
