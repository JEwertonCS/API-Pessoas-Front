package br.com.stefanini.desafioapipessoa.controller;

import br.com.stefanini.desafioapipessoa.RestTemplateConfig;
import br.com.stefanini.desafioapipessoa.model.Pessoa;
import br.com.stefanini.desafioapipessoa.utils.PageImplBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

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
    public String listar(Model model){

        ResponseEntity<PageImplBean<Pessoa>> result = null;
        try {
            result =
            restTemplate.exchange(URL_BASE, HttpMethod.GET, null,
                    new ParameterizedTypeReference<PageImplBean<Pessoa>>() { });
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        List<Pessoa> pessoas = result.getBody().getContent();
        model.addAttribute("pessoas", pessoas);
        return "pessoa/listar";
    }
}
