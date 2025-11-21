package org.homemade.common.event;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDataChangedEvent {
    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

}
