package com.nastech.upmureport;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nastech.upmureport.domain.dto.FileReqDto;

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
//    
//    @PostMapping(value = "/file")
//    public String savaFile(@RequestBody FileReqDto fileReqDto) {
//    	
//    	
//    	
//    }
    
    
}
