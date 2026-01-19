package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Bank;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BankImplTest {

    private final Bank bank = new BankImpl();

    @Test
    void createBankCard_credit_shouldReturnCreditBankCardWithDefaultLimit() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1));

        BankCard card = bank.createBankCard(user, BankCardType.CREDIT);

        assertInstanceOf(CreditBankCard.class, card);
        assertEquals(user, card.getUser());
        assertNotNull(card.getNumber());
        assertFalse(card.getNumber().isBlank());

        CreditBankCard credit = (CreditBankCard) card;
        assertEquals(1_000.0, credit.getCreditLimit());
    }

    @Test
    void createBankCard_debit_shouldReturnDebitBankCardWithDefaultBalance() {
        User user = new User("Jane", "Smith", LocalDate.of(1995, 5, 10));

        BankCard card = bank.createBankCard(user, BankCardType.DEBIT);

        assertInstanceOf(DebitBankCard.class, card);
        assertEquals(user, card.getUser());
        assertNotNull(card.getNumber());
        assertFalse(card.getNumber().isBlank());

        DebitBankCard debit = (DebitBankCard) card;
        assertEquals(0.0, debit.getBalance());
    }

    @Test
    void createBankCard_shouldGenerateDifferentNumbersForDifferentCards() {
        User user = new User("Alex", "Kim", LocalDate.of(2000, 3, 3));

        BankCard card1 = bank.createBankCard(user, BankCardType.DEBIT);
        BankCard card2 = bank.createBankCard(user, BankCardType.DEBIT);

        assertNotEquals(card1.getNumber(), card2.getNumber());
    }
}

