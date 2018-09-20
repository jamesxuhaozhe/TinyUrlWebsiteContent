package com.james.tinyurl.controller;

import com.james.tinyurl.handler.TinyURLHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * A debug controller that helps debugging
 * Created by haozhexu on 1/30/17.
 */
@Controller
public class TinyURLController {

    @Autowired
    private TinyURLHandler tinyURLHandler;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloWorld(Model model) {
        model.addAttribute("name", "James");
        return "welcome";
    }
}
