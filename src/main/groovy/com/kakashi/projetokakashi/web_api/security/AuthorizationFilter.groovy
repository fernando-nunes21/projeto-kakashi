package com.kakashi.projetokakashi.web_api.security

import com.kakashi.projetokakashi.web_api.service.impl.UserDetailsAdminService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter extends BasicAuthenticationFilter{

    private AuthenticationManager authenticationManager
    private JwtUtils jwtUtils
    private UserDetailsAdminService userDetailsAdminService

    AuthorizationFilter(
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            UserDetailsAdminService userDetailsAdminService
    ) {
        super(authenticationManager)
        this.jwtUtils = jwtUtils
        this.userDetailsAdminService = userDetailsAdminService
    }

    @Override
    void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization")
        if (authorization != null && authorization.startsWith("Bearer")){
            UsernamePasswordAuthenticationToken auth = getAuthentication(authorization.split(" ")[1])
            SecurityContextHolder.getContext().authentication = auth
        }
        chain.doFilter(request, response)
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if(!jwtUtils.isValidToken(token)) {
            throw new Exception("Token Invalido")
        }
        String tokenSubject = jwtUtils.getSubjectToken(token)
        UserDetails userDetails = userDetailsAdminService.loadUserByUsername(tokenSubject)
        return new UsernamePasswordAuthenticationToken(tokenSubject, null, userDetails.getAuthorities())
    }
}
