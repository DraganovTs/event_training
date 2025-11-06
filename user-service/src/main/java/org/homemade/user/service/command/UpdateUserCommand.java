package org.homemade.user.service.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.homemade.user.service.model.entity.Address;
import org.homemade.user.service.model.enums.AccountStatus;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UpdateUserCommand {

    @TargetAggregateIdentifier
    private final UUID userId;
    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final Address address;
    private final AccountStatus accountStatus;
    private final List<UUID> ownedProductIds;
}
