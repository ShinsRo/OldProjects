package com.midas2018.root.index;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping(value = "/")
    public String greeting(){
        return "MIDAS CHALLENGE 2018";
    };
}
