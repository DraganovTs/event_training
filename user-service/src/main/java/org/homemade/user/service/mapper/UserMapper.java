package org.homemade.user.service.mapper;

import jakarta.validation.Valid;
import org.homemade.user.service.model.dto.AddressDTO;
import org.homemade.user.service.model.dto.UserRequestDTO;
import org.homemade.user.service.model.dto.UserResponseDTO;
import org.homemade.user.service.model.entity.Address;
import org.homemade.user.service.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public User maoUserRequestDTOToUser(@Valid UserRequestDTO request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .address(mapAddressDTOToAddress(request.getAddress()))
                .build();

    }



    public UserResponseDTO mapUserToUserResponseDTO(User savedUser) {
        return UserResponseDTO.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .phone(savedUser.getPhone())
                .address(mapAddressToAddressDTO(savedUser.getAddress()))
                .accountStatus(savedUser.getAccountStatus().toString())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }

    private @Valid AddressDTO mapAddressToAddressDTO(@Valid Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    private @Valid Address mapAddressDTOToAddress(@Valid AddressDTO address) {
        return Address.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }
}
