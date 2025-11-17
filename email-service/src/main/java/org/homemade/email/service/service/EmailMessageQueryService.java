package org.homemade.email.service.service;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.homemade.email.service.mapper.EmailMessageServiceMapper;
import org.homemade.email.service.model.dto.EmailMessageResponseDTO;
import org.homemade.email.service.model.entity.EmailMessage;
import org.homemade.email.service.query.FindAllEmailMessages;
import org.homemade.email.service.query.FindEmailMessageQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailMessageQueryService {

    private final QueryGateway queryGateway;
    private final EmailMessageServiceMapper messageServiceMapper;

    public EmailMessageQueryService(QueryGateway queryGateway, EmailMessageServiceMapper messageServiceMapper) {
        this.queryGateway = queryGateway;
        this.messageServiceMapper = messageServiceMapper;
    }

    public EmailMessageResponseDTO findEmailMessageQuery(String recipient, String subject) {
        FindEmailMessageQuery findEmailMessageQuery = new FindEmailMessageQuery(recipient, subject);
        return messageServiceMapper.mapEmailMessageToEmailMessageResponse(queryGateway
                .query(findEmailMessageQuery, ResponseTypes.instanceOf(EmailMessage.class)).join());
    }

    public List<EmailMessageResponseDTO> findAllEmailMessages() {
        return queryGateway
                .query(new FindAllEmailMessages(), ResponseTypes.multipleInstancesOf(EmailMessage.class))
                .join()
                .stream()
                .map(messageServiceMapper::mapEmailMessageToEmailMessageResponse)
                .toList();
    }
}
