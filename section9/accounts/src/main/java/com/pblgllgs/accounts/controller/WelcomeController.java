package com.pblgllgs.accounts.controller;
/*
 *
 * @author pblgl
 * Created on 18-02-2025
 *
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sayHello")
public class WelcomeController {

    @GetMapping
    public String sayHello() {
        return "Hello World!";
    }
}
