package com.kakashi.projetokakashi.web_api.config

import com.kakashi.projetokakashi.web_api.repository.AdminRepository
import com.kakashi.projetokakashi.web_api.security.AuthenticationFilter
import com.kakashi.projetokakashi.web_api.security.AuthorizationFilter
import com.kakashi.projetokakashi.web_api.security.JwtUtils
import com.kakashi.projetokakashi.web_api.service.impl.UserDetailsAdminService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String botSendMessage = "/v1/kakashi/bot/chat"
    private AdminRepository adminRepository
    private UserDetailsAdminService userDetailsAdminService
    private JwtUtils jwtUtils


    SecurityConfig (AdminRepository adminRepository, UserDetailsAdminService userDetailsAdminService, JwtUtils jwtUtils) {
        this.adminRepository = adminRepository
        this.userDetailsAdminService = userDetailsAdminService
        this.jwtUtils = jwtUtils
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
        http.addFilter(new AuthenticationFilter(authenticationManager(), adminRepository, jwtUtils))
        http.addFilter(new AuthorizationFilter(authenticationManager(), jwtUtils, userDetailsAdminService))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Override
    void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs/**")
        web.ignoring().antMatchers("/swagger.json")
        web.ignoring().antMatchers("/swagger-ui.html")
        web.ignoring().antMatchers("/swagger-resources/**")
        web.ignoring().antMatchers("/webjars/**", "/configuration/ui", "/configuration/**")
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder()
    }
}
