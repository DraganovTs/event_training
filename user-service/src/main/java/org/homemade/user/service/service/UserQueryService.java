package org.homemade.user.service.service;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.homemade.common.model.dto.UserResponseDTO;
import org.homemade.user.service.mapper.UserMapper;
import org.homemade.user.service.model.entity.User;
import org.homemade.user.service.query.FindAllUsersQuery;
import org.homemade.user.service.query.FindUserQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserQueryService {

    private final QueryGateway queryGateway;
    private final UserMapper userMapper;


    public UserQueryService(QueryGateway queryGateway, UserMapper userMapper) {
        this.queryGateway = queryGateway;
        this.userMapper = userMapper;
    }


    public UserResponseDTO findUserQuery(String email) {
        FindUserQuery findUserQuery = new FindUserQuery(email);
        return queryGateway.query(findUserQuery, ResponseTypes.instanceOf(UserResponseDTO.class)).join();
    }

    public List<UserResponseDTO> getAllUsers() {
        System.out.println("user query service called");
        FindAllUsersQuery findAllUsersQuery = new FindAllUsersQuery();
        System.out.println("error is in findAllUsersQuery");
        List<User> result = queryGateway.query(findAllUsersQuery, ResponseTypes.multipleInstancesOf(User.class)).join();
        return result.stream().map(userMapper::mapUserToUserResponseDTO).toList();
    }
}
