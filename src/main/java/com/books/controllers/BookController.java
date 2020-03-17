package com.books.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {


    @GetMapping(path = "/")
    public String greeting(){
        return "Hello";
    }
}
