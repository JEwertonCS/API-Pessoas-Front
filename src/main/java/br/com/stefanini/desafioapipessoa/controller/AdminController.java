package br.com.stefanini.desafioapipessoa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/versao-2")
    public String indexVersao2(){
        return "index-2";
    }
}
