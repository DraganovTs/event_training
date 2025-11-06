package org.homemade.user.service.command.event;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDeletedEvent {
    private UUID userId;

}
