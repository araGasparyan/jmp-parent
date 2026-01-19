package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    private final Map<String, Subscription> subscriptions = new HashMap<>();
    private final Map<String, BankCard> cards = new HashMap<>();

    @Override
    public void subscribe(BankCard bankCard) {
        Optional.ofNullable(bankCard)
                .map(BankCard::getNumber)
                .ifPresent(cardNumber -> {
                    cards.putIfAbsent(cardNumber, bankCard);
                    subscriptions.putIfAbsent(
                            cardNumber,
                            new Subscription(cardNumber, LocalDate.now())
                    );
                });
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Optional.ofNullable(cardNumber)
                .map(subscriptions::get);
    }

    @Override
    public List<User> getAllUsers() {
        return cards.values().stream()
                .map(BankCard::getUser)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }
}
