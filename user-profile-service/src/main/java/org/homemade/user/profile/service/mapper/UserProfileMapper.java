package org.homemade.user.profile.service.mapper;

import org.homemade.common.event.UserDataChangedEvent;
import org.homemade.common.event.UserDataCreatedEvent;
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

    public UserProfile mapUserDataCreatedEventToUserProfile(UserDataCreatedEvent event) {
        return UserProfile.builder()
                .userId(event.getUserId())
                .username(event.getUsername())
                .email(event.getEmail())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .phone(event.getPhone())
                .products(new ArrayList<>())
                .emails(new ArrayList<>())
                .build();
    }

    public void mapUserDataChangedEventToUserProfile(UserProfile userProfileToSave, UserDataChangedEvent event) {
        userProfileToSave.setUsername(event.getUsername());
        userProfileToSave.setFirstName(event.getFirstName());
        userProfileToSave.setLastName(event.getLastName());
        userProfileToSave.setPhone(event.getPhone());
        userProfileToSave.setEmail(event.getEmail());
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
