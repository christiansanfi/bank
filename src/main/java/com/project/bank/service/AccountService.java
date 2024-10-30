
package com.project.bank.service;

import com.project.bank.dto.*;
import com.project.bank.model.Account;
import com.project.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

}