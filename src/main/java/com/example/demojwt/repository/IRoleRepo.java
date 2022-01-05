package com.example.demojwt.repository;

import com.example.demojwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepo extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
