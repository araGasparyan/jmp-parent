package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);

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

    static boolean isPayableUser(User user) {
        return Optional.ofNullable(user)
                .map(User::getBirthday)
                .map(birthday -> ChronoUnit.YEARS.between(birthday, LocalDate.now()))
                .map(age -> age >= 18)
                .orElse(false);
    }
}
