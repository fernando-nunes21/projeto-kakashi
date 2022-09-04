package com.kakashi.projetokakashi.web_api.service.impl

import com.kakashi.projetokakashi.web_api.contract.entity.AdminEntity
import com.kakashi.projetokakashi.web_api.repository.AdminRepository
import com.kakashi.projetokakashi.web_api.security.AdminDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsAdminService implements UserDetailsService {

    private AdminRepository adminRepository

    UserDetailsAdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository
    }

    @Override
    UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        AdminEntity admin = adminRepository.findById(id.toInteger()).orElseThrow{ new Exception("Admin Invalido") }
        return new AdminDetails(admin)
    }
}
