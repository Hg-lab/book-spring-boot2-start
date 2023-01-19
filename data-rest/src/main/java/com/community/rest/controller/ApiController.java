package com.community.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ApiController {

    @GetMapping({"/", ""})
    public String init() {
        return "redirect:/api";
    }
}
