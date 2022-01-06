package com.example.demojwt.service;

import com.example.demojwt.model.Account;
import com.example.demojwt.repository.IAccountRepo;
import com.example.demojwt.security.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private IAccountRepo iAccountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = (Account) iAccountRepo.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AccountDetails(account);
    }

}
