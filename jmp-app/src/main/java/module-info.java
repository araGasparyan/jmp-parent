module jmp.app {
    requires jmp.dto;

    requires jmp.bank.api;
    requires jmp.service.api;

    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;

    uses com.epam.jmp.bank.Bank;
    uses com.epam.jmp.service.Service;
}
