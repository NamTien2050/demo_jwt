package com.example.demojwt.controller;

import com.example.demojwt.repository.IAccountRepo;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private IAccountRepo iAccountRepo;

}
