package com.example.demojwt.controller;

import com.example.demojwt.dto.AccountDTO;
import com.example.demojwt.model.Account;
import com.example.demojwt.repository.IAccountRepo;
import com.example.demojwt.security.JwtTokenProvider;
import com.example.demojwt.service.AccountService;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.remote.JMXAuthenticator;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private IAccountRepo iAccountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public Account createAccount(@RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        iAccountRepo.save(account);
        return account;
    }

    @PostMapping("/login")
    public String Login(@RequestParam String username, @RequestParam String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = accountService.loadUserByUsername(username);
            return jwtTokenProvider.generateToken(userDetails);
        } catch (Exception e) {
            System.out.println(" sai mk hoặc tk");
            return null;
        }
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN"})
    public List<Account> getAll() {
        return iAccountRepo.findAll();
    }

}
