package com.epam.jmp.service.exception;

public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException(String cardNumber) {
        super("Subscription not found for bank card number: " + cardNumber);
    }
}