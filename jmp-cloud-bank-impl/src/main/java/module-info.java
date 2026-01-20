module jmp.cloud.bank.impl {
    requires transitive jmp.bank.api;
    requires jmp.dto;

    exports com.epam.jmp.bank.impl;

    provides com.epam.jmp.bank.Bank with com.epam.jmp.bank.impl.BankImpl;
}
