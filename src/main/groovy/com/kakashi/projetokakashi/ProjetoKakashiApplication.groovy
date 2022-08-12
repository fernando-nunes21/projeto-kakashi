package com.kakashi.projetokakashi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class ProjetoKakashiApplication {

	static void main(String[] args) {
		SpringApplication.run(ProjetoKakashiApplication, args)
	}

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		restTemplateBuilder.build()
	}

}
