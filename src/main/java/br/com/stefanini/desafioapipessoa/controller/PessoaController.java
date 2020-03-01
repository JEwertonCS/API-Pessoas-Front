package br.com.stefanini.desafioapipessoa.controller;

import br.com.stefanini.desafioapipessoa.RestTemplateConfig;
import br.com.stefanini.desafioapipessoa.model.Pessoa;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PessoaController {

    private RestTemplate restTemplate = RestTemplateConfig.getInstance();
    private static String URL_BASE = "http://3.210.232.150:8080/pessoas";
    private static String URL_BASE_VERSAO2 = "http://3.210.232.150:8080/pessoas/versao2";

    @GetMapping("/pessoa/cadastrar")
    public String cadastrar(Pessoa pessoa){
        return "pessoa/cadastrar";
    }

    @PostMapping("pessoa/salvar")
    public String salvar(@Valid @ModelAttribute Pessoa pessoa, BindingResult result, RedirectAttributes attributes){

        if ( result.hasErrors() ){
            return cadastrar(pessoa);
        }

        Pessoa pessoa1 = restTemplate.postForObject( URL_BASE, pessoa, Pessoa.class );
        System.out.println( pessoa1 );
        attributes.addFlashAttribute( "mensagem", "Salvo com sucesso!" );
        return "redirect:/";
    }

    @GetMapping("pessoa/listar")
    public String listar( @ModelAttribute Pessoa pessoa ){
        Pessoa forObject = restTemplate.getForObject(URL_BASE, Pessoa.class);
//        System.out.println( forObject );
//        System.out.println( "forObject" );

        return "pessoa/listar";
    }
}
