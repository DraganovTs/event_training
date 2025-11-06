package org.homemade.user.service.command.event;

import lombok.Data;
import org.homemade.user.service.model.entity.Address;
import org.homemade.user.service.model.enums.AccountStatus;

import java.util.List;
import java.util.UUID;

@Data
public class UserUpdatedEvent {
    private UUID userId;
    private  String username;
    private  String email;
    private  String firstName;
    private  String lastName;
    private  String phone;
    private Address address;
    private AccountStatus accountStatus;
    private List<UUID> ownedProductIds;
}
