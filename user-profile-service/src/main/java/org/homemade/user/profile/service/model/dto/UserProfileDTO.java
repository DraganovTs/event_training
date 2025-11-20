package org.homemade.user.profile.service.model.dto;

import lombok.Builder;
import lombok.Data;


import java.util.List;

@Data
@Builder
public class UserProfileDTO {
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private List<ProductInfoDTO> products;
    private List<EmailsInfoDTO> emails;

}
