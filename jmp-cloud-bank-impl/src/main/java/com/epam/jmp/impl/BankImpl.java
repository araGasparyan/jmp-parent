package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Bank;

import java.util.UUID;

public class BankImpl implements Bank {

    private static final double DEFAULT_CREDIT_LIMIT = 1_000.0;
    private static final double DEFAULT_DEBIT_BALANCE = 0.0;

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        return switch (cardType) {
            case CREDIT -> new CreditBankCard(generateCardNumber(), user, DEFAULT_CREDIT_LIMIT);
            case DEBIT  -> new DebitBankCard(generateCardNumber(), user, DEFAULT_DEBIT_BALANCE);
        };
    }

    private String generateCardNumber() {
        return UUID.randomUUID().toString();
    }
}
