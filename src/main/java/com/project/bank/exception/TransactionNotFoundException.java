package com.project.bank.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message){
        super(message);
    }
}
