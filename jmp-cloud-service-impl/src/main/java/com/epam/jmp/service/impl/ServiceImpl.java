package com.epam.jmp.service.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Service;
import com.epam.jmp.service.exception.SubscriptionNotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
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
        var subscription = Optional.ofNullable(cardNumber)
                .map(subscriptions::get)
                .orElseThrow(() -> new SubscriptionNotFoundException(cardNumber));

        return Optional.of(subscription);
    }

    @Override
    public List<User> getAllUsers() {
        return cards.values().stream()
                .map(BankCard::getUser)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toUnmodifiableList()); // can be changed to Streams toList
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        var predicate = Optional.ofNullable(condition).orElse(s -> true);

        return subscriptions.values().stream()
                .filter(predicate)
                .collect(Collectors.toUnmodifiableList());
    }
}
