package com.backend.birdmeal.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value="/api/html", produces = MediaType.APPLICATION_JSON_VALUE)
public class HtmlController {

    @GetMapping(value = "")
    public String htmlFile(){
        return "address";
    }
}

