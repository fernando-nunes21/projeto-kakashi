package com.kakashi.projetokakashi.web_api.security

import com.kakashi.projetokakashi.web_api.contract.entity.AdminEntity
import com.kakashi.projetokakashi.web_api.enums.Profile
import org.hibernate.engine.internal.Collections
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AdminDetails implements UserDetails{

    private AdminEntity adminEntity

    AdminDetails(AdminEntity adminEntity) {
        this.adminEntity = adminEntity
    }

    String adminId = adminEntity.id

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorities
                    = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"))
            return authorities;
    }

    @Override
    String getPassword() {
        return adminEntity.password
    }

    @Override
    String getUsername() {
        return adminEntity.id.toString()
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return true
    }
}
