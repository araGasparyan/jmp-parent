package com.epam.jmp.impl;

import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Service;
import com.epam.jmp.service.impl.ServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceImplTest {

    private final Service service = new ServiceImpl();

    @Test
    void subscribe_shouldCreateSubscriptionForCardNumber() {
        String cardNumber = "CARD-1";
        var user = new User("John", "Doe", LocalDate.of(1990, 1, 1));
        var card = new CreditBankCard(cardNumber, user, 1000.0);

        service.subscribe(card);

        var subscriptionOpt = service.getSubscriptionByBankCardNumber(cardNumber);
        assertTrue(subscriptionOpt.isPresent());

        var subscription = subscriptionOpt.get();
        assertEquals(cardNumber, subscription.getBankcard());
        assertEquals(LocalDate.now(), subscription.getStartDate());
    }

    @Test
    void subscribe_nullBankCard_shouldDoNothing() {
        assertDoesNotThrow(() -> service.subscribe(null));
        assertTrue(service.getAllUsers().isEmpty());
    }

    @Test
    void getSubscriptionByBankCardNumber_nullCardNumber_shouldReturnEmpty() {
        assertTrue(service.getSubscriptionByBankCardNumber(null).isEmpty());
    }

    @Test
    void getSubscriptionByBankCardNumber_unknownCardNumber_shouldReturnEmpty() {
        assertTrue(service.getSubscriptionByBankCardNumber("UNKNOWN").isEmpty());
    }

    @Test
    void getAllUsers_shouldReturnDistinctUsers() {
        var sameUser = new User("Jane", "Smith", LocalDate.of(1995, 5, 10));

        var card1 = new CreditBankCard("CARD-1", sameUser, 500.0);
        var card2 = new CreditBankCard("CARD-2", sameUser, 700.0);

        service.subscribe(card1);
        service.subscribe(card2);

        var users = service.getAllUsers();
        assertEquals(1, users.size());
        assertEquals(sameUser, users.get(0));
    }

    @Test
    void getAllUsers_shouldIgnoreNullUsers() {
        var cardWithNullUser = new CreditBankCard("CARD-NULL", null, 1000.0);

        service.subscribe(cardWithNullUser);

        assertTrue(service.getAllUsers().isEmpty());
    }

    @Test
    void subscribe_sameCardNumberTwice_shouldKeepFirstCardAndUser() {
        var firstUser = new User("Alex", "Kim", LocalDate.of(2000, 3, 3));
        var secondUser = new User("Bob", "Lee", LocalDate.of(1999, 2, 2));

        var first = new CreditBankCard("CARD-1", firstUser, 1000.0);
        var second = new CreditBankCard("CARD-1", secondUser, 2000.0);

        service.subscribe(first);
        service.subscribe(second);

        List<User> users = service.getAllUsers();

        assertEquals(1, users.size());
        assertEquals(firstUser, users.get(0));
    }

    @Test
    void getAverageUsersAge_twoUsers_shouldReturnAverage() {
        var now = LocalDate.now();

        var user1 = new User("John", "Doe", now.minusYears(30));
        var user2 = new User("Jane", "Smith", now.minusYears(20));

        var card1 = new CreditBankCard("CARD-AGE-1", user1, 1000.0);
        var card2 = new CreditBankCard("CARD-AGE-2", user2, 1000.0);

        service.subscribe(card1);
        service.subscribe(card2);

        var averageAge = service.getAverageUsersAge();

        assertEquals(25.0, averageAge);
    }
}
