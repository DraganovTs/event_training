package org.homemade.common.event;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailDataChangedEvent {

    private UUID emailId;
    private String recipient;
    private String subject;
    private String body;
    private String status;
}
