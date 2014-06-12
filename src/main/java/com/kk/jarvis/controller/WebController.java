package com.kk.jarvis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 9:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/welcome")
public class WebController {
    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {

        model.addAttribute("message", "Spring 3 MVC Hello World");
        return "sample";

    }
}
