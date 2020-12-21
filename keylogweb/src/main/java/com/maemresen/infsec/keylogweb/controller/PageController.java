package com.maemresen.infsec.keylogweb.controller;

import com.maemresen.infsec.keylogweb.service.IKeyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    @Autowired
    private IKeyLogService keyLogService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("keyLogList", keyLogService.getKeyLogList());
        return "home";
    }

}
