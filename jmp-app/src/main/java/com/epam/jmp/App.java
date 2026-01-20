package com.epam.jmp;

import com.epam.jmp.bank.Bank;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.util.ServiceLoader;

public class App {
    public static void main(String[] args) {
        var bank = load(Bank.class);
        var service = load(Service.class);

        var user = new User("John", "Doe", LocalDate.of(2010, 1, 1));

        var card = bank.createBankCard(user, BankCardType.CREDIT);
        System.out.println("Created card: " + card.getNumber());

        service.subscribe(card);

        var subscription = service.getSubscriptionByBankCardNumber(card.getNumber());
        System.out.println("Subscription exists: " + subscription.isPresent());

        var users = service.getAllUsers();
        System.out.println("Users count: " + users.size());
        users.forEach(u -> System.out.println(u.getName() + " " + u.getSurname()));

        double averageAge = service.getAverageUsersAge();
        System.out.println("Average users age: " + averageAge);

        System.out.println("Is payable user: " + Service.isPayableUser(user));

        var todaysSubscriptions = service.getAllSubscriptionsByCondition(
                s -> LocalDate.now().equals(s.getStartDate())
        );

        System.out.println("Today's subscriptions: " + todaysSubscriptions.size());
        todaysSubscriptions.forEach(s ->
                System.out.println("Subscription: " + s.getBankcard() + " since " + s.getStartDate())
        );

    }

    private static <T> T load(Class<T> api) {
        return ServiceLoader.load(api)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No implementation found for " + api.getName()));
    }
}
