package com.james.tinyurl.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by haozhexu on 1/30/17.
 */
@ComponentScan
@Controller
public class TinyURLController {


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloWorld(Model model) {
        model.addAttribute("name", "James");
        return "welcome";
    }
}
