package com.kakashi.projetokakashi.web_api.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.kakashi.projetokakashi.web_api.contract.LoginRequest
import com.kakashi.projetokakashi.web_api.repository.AdminRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AdminRepository adminRepository
    private AuthenticationManager authenticationManager

    AuthenticationFilter(AuthenticationManager authenticationManager, AdminRepository adminRepository) {
        this.adminRepository = adminRepository
        this.authenticationManager = authenticationManager
    }

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.inputStream, LoginRequest.class)

            Integer adminId = adminRepository.findAdminByLogin(loginRequest.username).id
            def authToken = new UsernamePasswordAuthenticationToken(adminId, loginRequest.password)
            return authenticationManager.authenticate(authToken)
        } catch(Exception ex) {
            throw new Exception(ex)
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String id = (authResult.principal as AdminDetails).adminId
        response.addHeader("Authorization", "teste123")
    }
}
