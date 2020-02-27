package br.com.stefanini.desafioapipessoa.controller;

import br.com.stefanini.desafioapipessoa.model.Pessoa;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class PessoaController {

    @GetMapping("/pessoa/cadastrar")
    public String cadastrar(Model model){

        model.addAttribute("pessoa", new Pessoa());
        return "pessoa/cadastrar";
    }

    @PostMapping("pessoa/salvar")
    public String salvar(@ModelAttribute Pessoa pessoa){

        System.out.println( pessoa );

        //Criar um factory para utilizar o RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        return "redirect:/";

    }
}
