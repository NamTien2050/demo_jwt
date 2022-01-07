package com.example.demojwt.service;

import com.example.demojwt.model.Account;
import com.example.demojwt.repository.IAccountRepo;
import com.example.demojwt.security.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private IAccountRepo iAccountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        Account account = iAccountRepo.findByUsername(username);
        list.add(new SimpleGrantedAuthority(account.getRoles()));
        if (account == null) {
            throw new UsernameNotFoundException("Bad credentials");
        }
        return new User(account.getUsername(),account.getPassword(),list);
    }
}
