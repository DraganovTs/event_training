package org.homemade.user.profile.service.mapper;

import org.homemade.common.event.UserDataChangedEvent;
import org.homemade.user.profile.service.model.dto.EmailsInfoDTO;
import org.homemade.user.profile.service.model.dto.ProductInfoDTO;
import org.homemade.user.profile.service.model.dto.UserProfileDTO;
import org.homemade.user.profile.service.model.entity.EmailsInfo;
import org.homemade.user.profile.service.model.entity.ProductInfo;
import org.homemade.user.profile.service.model.entity.UserProfile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserProfileMapper {

    public UserProfile mapUserDataChangedEventToUserProfile(UserProfile userProfile, UserDataChangedEvent event) {
        if (userProfile.getEmail().isEmpty()) {
            return mapToNewProfile(userProfile, event);
        } else {
            return mapToExistingProfile(userProfile, event);
        }
    }

    private UserProfile mapToExistingProfile(UserProfile userProfile, UserDataChangedEvent event) {
        userProfile.setEmail(event.getEmail());
        userProfile.setFirstName(event.getFirstName());
        userProfile.setLastName(event.getLastName());
        userProfile.setPhone(event.getPhone());
        return userProfile;
    }

    private UserProfile mapToNewProfile(UserProfile userProfile, UserDataChangedEvent event) {
        return UserProfile.builder()
                .userId(userProfile.getUserId())
                .username(userProfile.getUsername())
                .email(event.getEmail())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .phone(event.getPhone())
                .products(new ArrayList<>())
                .emails(new ArrayList<>())
                .build();
    }

    public UserProfileDTO mapUserProfileToUserProfileDTO(UserProfile userProfile) {
        return UserProfileDTO.builder()
                .userId(userProfile.getUserId())
                .username(userProfile.getUsername())
                .email(userProfile.getEmail())
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .phone(userProfile.getPhone())
                .products(mapProductInfoToDTO(userProfile.getProducts()))
                .emails(mapUserProfileEmailsToDTO(userProfile.getEmails()))
                .build();
    }

    private List<EmailsInfoDTO> mapUserProfileEmailsToDTO(List<EmailsInfo> emails) {
        return emails.stream().map(emailsInfo -> EmailsInfoDTO.builder()
                        .emailId(emailsInfo.getEmailId())
                        .recipient(emailsInfo.getRecipient())
                        .subject(emailsInfo.getSubject())
                        .body(emailsInfo.getBody())
                        .status(emailsInfo.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ProductInfoDTO> mapProductInfoToDTO(List<ProductInfo> products) {
        return products.stream().map(productInfo -> ProductInfoDTO.builder()
                        .productId(productInfo.getProductId())
                        .name(productInfo.getName())
                        .brand(productInfo.getBrand())
                        .description(productInfo.getDescription())
                        .category(productInfo.getCategory())
                        .price(productInfo.getPrice())
                        .unitsInStock(productInfo.getUnitsInStock())
                        .build())
                .collect(Collectors.toList());
    }
}
