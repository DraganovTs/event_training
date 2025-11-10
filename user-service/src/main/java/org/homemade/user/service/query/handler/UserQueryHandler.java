package org.homemade.user.service.query.handler;

import org.axonframework.queryhandling.QueryHandler;
import org.homemade.common.model.dto.UserResponseDTO;
import org.homemade.user.service.model.entity.User;
import org.homemade.user.service.query.FindAllUsersQuery;
import org.homemade.user.service.query.FindUserQuery;
import org.homemade.user.service.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserQueryHandler {

    private final UserService userService;

    public UserQueryHandler(UserService userService) {
        this.userService = userService;
    }

    @QueryHandler
    public UserResponseDTO findUser(FindUserQuery findUserQuery){
       return userService.getUserByEmail(findUserQuery);
    }

    @QueryHandler
    public List<User> findAllUsers(FindAllUsersQuery findAllUsersQuery){
        return userService.findAllUsers();
    }
}
