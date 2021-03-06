package br.com.stefanini.desafioapipessoa.controller;

import br.com.stefanini.desafioapipessoa.RestTemplateConfig;
import br.com.stefanini.desafioapipessoa.model.Pessoa;
import br.com.stefanini.desafioapipessoa.utils.PageImplBean;
import br.com.stefanini.desafioapipessoa.utils.Util;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PessoaController {

    private RestTemplate restTemplate = RestTemplateConfig.getInstance();
    private static final String URL_BASE = "http://3.210.232.150:8080/pessoas";

    @GetMapping("/pessoa/cadastrar")
    public String cadastrar(Pessoa pessoa){
        return "pessoa/cadastrar";
    }

    @PostMapping("pessoa/salvar")
    public String salvar(@Valid @ModelAttribute Pessoa pessoa, BindingResult result, RedirectAttributes attributes){

        if ( result.hasErrors() ){
            return cadastrar(pessoa);
        }

        try {
            Pessoa pessoaSalva = restTemplate.postForObject( URL_BASE, pessoa, Pessoa.class );
            attributes.addFlashAttribute( "mensagem", "Salvo com sucesso!" );
            return "redirect:/";
        } catch ( Exception e){
            ObjectError error = new ObjectError("", e.getMessage() );
            result.addError( error );
            return cadastrar(pessoa);
        }

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

    @GetMapping("/pessoa/visualizar/{cpf}")
    public String visualizar(@PathVariable String cpf, Model model){

        ResponseEntity<PageImplBean<Pessoa>> result = null;
        String cpfParm = Util.retiraFormatacaoCpf(cpf);
        String add = "?cpf="+cpfParm;

        try {
            result =
                    restTemplate.exchange(URL_BASE + add, HttpMethod.GET, null,
                            new ParameterizedTypeReference<PageImplBean<Pessoa>>() { });
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        List<Pessoa> pessoas = result.getBody().getContent();
        model.addAttribute("pessoa", pessoas.get(0));

        return "pessoa/visualizar";
    }

    @GetMapping("/pessoa/editar/{cpf}")
    public String editar(@PathVariable String cpf, Model model){

        ResponseEntity<PageImplBean<Pessoa>> result = null;
        String cpfParm = Util.retiraFormatacaoCpf(cpf);
        String add = "?cpf="+cpfParm;

        try {
            result =
                    restTemplate.exchange(URL_BASE + add, HttpMethod.GET, null,
                            new ParameterizedTypeReference<PageImplBean<Pessoa>>() { });
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        List<Pessoa> pessoas = result.getBody().getContent();
        model.addAttribute("pessoa", pessoas.get(0));

        return "pessoa/editar";
    }

    @PostMapping("/pessoa/atualizar/{id}")
    public String atualizar( @Valid @ModelAttribute Pessoa updatedPessoa, @PathVariable String id, Model model,
                             BindingResult result, RedirectAttributes attributes ){
        if ( result.hasErrors() ){
            return editar(updatedPessoa.getCpf(),  model);
        }

        restTemplate.put( URL_BASE.concat("/") + id , updatedPessoa );
        attributes.addFlashAttribute( "mensagem", "Editado com sucesso!" );
        return "redirect:/";
    }

    @GetMapping("/pessoa/excluir/{id}")
    public String excluir( @PathVariable String id, RedirectAttributes attributes){
        restTemplate.delete( URL_BASE.concat("/") + id );
        attributes.addFlashAttribute( "mensagem", "Excluido com sucesso!" );
        return "redirect:/";
    }
}
