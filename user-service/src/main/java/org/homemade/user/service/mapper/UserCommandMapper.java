package org.homemade.user.service.mapper;

import jakarta.validation.Valid;
import org.homemade.common.model.dto.AddressDTO;
import org.homemade.user.service.command.CreateUserCommand;
import org.homemade.user.service.command.UpdateUserCommand;
import org.homemade.user.service.model.dto.UserRequestDTO;
import org.homemade.user.service.model.entity.Address;
import org.homemade.user.service.model.enums.AccountStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserCommandMapper {
    public CreateUserCommand mapToCreateUserCommand(@Valid UserRequestDTO request) {
        return CreateUserCommand.builder()
                .userId(UUID.randomUUID())
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .address(mapAddress(request.getAddress()))
                .accountStatus(AccountStatus.PENDING)
                .ownedProductIds(new ArrayList<>())
                .build();


    }

    public UpdateUserCommand mapToUpdateUserCommand(@Valid UserRequestDTO request) {
        return UpdateUserCommand.builder()
                .userId(request.getUserId())
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .address(mapAddress(request.getAddress()))
                .accountStatus(AccountStatus.PENDING)
                .ownedProductIds(request.getProductIds())
                .build();
    }

    private Address mapAddress(@Valid AddressDTO address) {
        return Address.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

}
