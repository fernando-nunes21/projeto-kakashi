package com.kakashi.projetokakashi.web_api.repository

import com.kakashi.projetokakashi.web_api.contract.entity.AdminEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface AdminRepository extends CrudRepository<AdminEntity, Integer> {

    @Query(value="SELECT * FROM Admins WHERE username = ?1", nativeQuery = true)
    AdminEntity findAdminByLogin(String username)

}