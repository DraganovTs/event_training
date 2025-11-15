package org.homemade.email.service.query;

import lombok.Value;

@Value
public class FindEmailMessageQuery {
    String recipient;
    String subject;
}
