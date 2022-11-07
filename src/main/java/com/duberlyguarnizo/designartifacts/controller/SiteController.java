package com.duberlyguarnizo.designartifacts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteController {
    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        return "admin";
    }

    @PostMapping("/comentarios")
    public void processComments(@RequestBody String  content) {
        //content tiene nombre, correo y comentario como atributos
        //procesar comentario:enviar correo con datos a duberlygfr@gmail.com
    }
}
