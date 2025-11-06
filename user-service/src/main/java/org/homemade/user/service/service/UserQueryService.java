package org.homemade.user.service.service;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.homemade.common.model.dto.UserResponseDTO;
import org.homemade.user.service.query.FindUserQuery;
import org.springframework.stereotype.Service;

@Service
public class UserQueryService {

    private final QueryGateway queryGateway;

    public UserQueryService(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }


    public UserResponseDTO findUserQuery(String email) {
        FindUserQuery findUserQuery = new FindUserQuery(email);
        return queryGateway.query(findUserQuery, ResponseTypes.instanceOf(UserResponseDTO.class)).join();
    }
}
