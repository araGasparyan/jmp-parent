package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface Service {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    default double getAverageUsersAge() {
        var now = LocalDate.now();

        return getAllUsers().stream()
                .map(User::getBirthday)
                .filter(Objects::nonNull)
                .mapToDouble(birthday ->
                        ChronoUnit.YEARS.between(birthday, now)
                )
                .average()
                .orElse(0.0);
    }
}
