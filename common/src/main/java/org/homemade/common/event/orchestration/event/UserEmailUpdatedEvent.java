package org.homemade.common.event.orchestration.event;

import lombok.Data;

import java.util.UUID;

@Data
public class UserEmailUpdatedEvent {
    private  UUID userId;
    private  String email;
    private  String newEmail;


}
