module jmp.cloud.service.impl {
    requires transitive jmp.service.api;
    requires jmp.dto;

    exports com.epam.jmp.service.impl;

    provides com.epam.jmp.service.Service with com.epam.jmp.service.impl.ServiceImpl;
}
