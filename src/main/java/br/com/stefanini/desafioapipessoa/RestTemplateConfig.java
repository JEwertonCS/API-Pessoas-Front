package br.com.stefanini.desafioapipessoa;

import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

public class RestTemplateConfig {
	
	private static RestTemplate instance;

	private  RestTemplateConfig(){

	}

	public static RestTemplate getInstance(){
		if ( instance == null ) {
			instance = new RestTemplate();
			instance.getInterceptors().add( new BasicAuthenticationInterceptor("admin", "123456") );
		}
		return instance;
	}
}
