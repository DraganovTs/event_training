package org.homemade.user.profile.service.query.conroller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.homemade.user.profile.service.model.dto.UserProfileDTO;
import org.homemade.user.profile.service.query.FindUserProfileQuery;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/userProfile", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserQueryProfileController {
    private final QueryGateway queryGateway;

    public UserQueryProfileController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping()
    public ResponseEntity<UserProfileDTO> getUserProfile(@RequestParam("username") String username) {
        FindUserProfileQuery findUserProfileQuery = new FindUserProfileQuery(username);
        UserProfileDTO userProfileDTO = queryGateway.query(findUserProfileQuery, ResponseTypes
                .instanceOf(UserProfileDTO.class)).join();
        return ResponseEntity.ok(userProfileDTO);
    }


}
