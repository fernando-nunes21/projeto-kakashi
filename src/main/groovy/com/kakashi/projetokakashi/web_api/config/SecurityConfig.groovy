package com.kakashi.projetokakashi.web_api.config

import com.kakashi.projetokakashi.web_api.repository.AdminRepository
import com.kakashi.projetokakashi.web_api.security.AuthenticationFilter
import com.kakashi.projetokakashi.web_api.service.impl.UserDetailsAdminService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String adminAuthRoute = "/v1/kakashi/bot/admin/auth"
    private final String botSendMessage = "/v1/kakashi/bot/chat"
    private AdminRepository adminRepository
    private UserDetailsAdminService userDetailsAdminService

    SecurityConfig (AdminRepository adminRepository, UserDetailsAdminService userDetailsAdminService) {
        this.adminRepository = adminRepository
        this.userDetailsAdminService = userDetailsAdminService
    }

    @Override
    void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsAdminService).passwordEncoder(bCryptPasswordEncoder())
    }

    @Override
    void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
        http.authorizeRequests()
                .antMatchers(botSendMessage)
                .permitAll()
                .anyRequest()
                .authenticated()
        //TODO - CASO ESTOURE ERRO, REVISAR ESSA PARTE
        http.addFilter(new AuthenticationFilter(authenticationManager(), adminRepository))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder()
    }
}
