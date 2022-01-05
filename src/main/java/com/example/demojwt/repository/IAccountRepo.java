package com.example.demojwt.repository;

import com.example.demojwt.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepo extends JpaRepository<Account, Long> {
    UserDetails findByUsername(String username);
}
