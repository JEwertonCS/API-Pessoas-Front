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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PessoaVersao2Controller {

    private RestTemplate restTemplate = RestTemplateConfig.getInstance();
    private static final String URL_BASE_VERSAO2 = "http://3.210.232.150:8080/pessoas/versao2";

    @GetMapping("/pessoaVersao2/cadastrar")
    public String cadastrar(Pessoa pessoa){
        return "pessoaVersao2/cadastrar";
    }

    @PostMapping("pessoaVersao2/salvar")
    public String salvar(@Valid @ModelAttribute Pessoa pessoa, BindingResult result, RedirectAttributes attributes){

        if ( result.hasErrors() ){
            return cadastrar(pessoa);
        }

        try {
            Pessoa pessoaSalva = restTemplate.postForObject( URL_BASE_VERSAO2, pessoa, Pessoa.class );
            attributes.addFlashAttribute( "mensagem", "Salvo com sucesso!" );
            return "redirect:/";
        } catch ( Exception e){
            ObjectError error = new ObjectError("", e.getMessage() );
            result.addError( error );
            return cadastrar(pessoa);
        }

    }

    @GetMapping("pessoaVersao2/listar")
    public String listar(Model model){

        ResponseEntity<PageImplBean<Pessoa>> result = null;
        try {
            result =
            restTemplate.exchange(URL_BASE_VERSAO2, HttpMethod.GET, null,
                    new ParameterizedTypeReference<PageImplBean<Pessoa>>() { });
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        List<Pessoa> pessoas = result.getBody().getContent();
        model.addAttribute("pessoas", pessoas);
        return "pessoaVersao2/listar";
    }

    @GetMapping("/pessoaVersao2/visualizar/{cpf}")
    public String visualizar(@PathVariable String cpf, Model model){

        ResponseEntity<PageImplBean<Pessoa>> result = null;
        String cpfParm = Util.retiraFormatacaoCpf(cpf);
        String add = "?cpf="+cpfParm;

        try {
            result =
                    restTemplate.exchange(URL_BASE_VERSAO2 + add, HttpMethod.GET, null,
                            new ParameterizedTypeReference<PageImplBean<Pessoa>>() { });
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        List<Pessoa> pessoas = result.getBody().getContent();
        model.addAttribute("pessoa", pessoas.get(0));

        return "pessoaVersao2/visualizar";
    }

    @GetMapping("/pessoaVersao2/editar/{cpf}")
    public String editar(@PathVariable String cpf, Model model){

        ResponseEntity<PageImplBean<Pessoa>> result = null;
        String cpfParm = Util.retiraFormatacaoCpf(cpf);
        String add = "?cpf="+cpfParm;

        try {
            result =
                    restTemplate.exchange(URL_BASE_VERSAO2 + add, HttpMethod.GET, null,
                            new ParameterizedTypeReference<PageImplBean<Pessoa>>() { });
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        List<Pessoa> pessoas = result.getBody().getContent();
        model.addAttribute("pessoa", pessoas.get(0));

        return "pessoaVersao2/editar";
    }

    @PostMapping("/pessoaVersao2/atualizar/{id}")
    public String atualizar( @Valid @ModelAttribute Pessoa updatedPessoa, @PathVariable String id, Model model,
                             BindingResult result, RedirectAttributes attributes ){
        if ( result.hasErrors() ){
            return editar(updatedPessoa.getCpf(),  model);
        }

        restTemplate.put( URL_BASE_VERSAO2.concat("/") + id , updatedPessoa );
        attributes.addFlashAttribute( "mensagem", "Editado com sucesso!" );
        return "redirect:/";
    }

    @GetMapping("/pessoaVersao2/excluir/{id}")
    public String excluir( @PathVariable String id, RedirectAttributes attributes){
        restTemplate.delete( URL_BASE_VERSAO2.concat("/") + id );
        attributes.addFlashAttribute( "mensagem", "Excluido com sucesso!" );
        return "redirect:/";
    }
}
