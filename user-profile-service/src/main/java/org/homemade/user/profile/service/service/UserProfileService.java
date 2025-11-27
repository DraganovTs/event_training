package org.homemade.user.profile.service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.homemade.common.event.ProductDataChangedEvent;
import org.homemade.common.event.ProductDataCreatedEvent;
import org.homemade.common.event.UserDataChangedEvent;
import org.homemade.common.event.UserDataCreatedEvent;
import org.homemade.user.profile.service.exception.UserProfileAlreadyExist;
import org.homemade.user.profile.service.exception.UserProfileNotExist;
import org.homemade.user.profile.service.mapper.ProductInfoMapper;
import org.homemade.user.profile.service.mapper.UserProfileMapper;
import org.homemade.user.profile.service.model.dto.UserProfileDTO;
import org.homemade.user.profile.service.model.entity.ProductInfo;
import org.homemade.user.profile.service.model.entity.UserProfile;
import org.homemade.user.profile.service.query.FindUserProfileQuery;
import org.homemade.user.profile.service.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final ProductInfoMapper productInfoMapper;
    private final ProductInfoService productInfoService;

    public void handleUserDataCreatedEvent(UserDataCreatedEvent event) {
        checkIfUserAlreadyExists(event.getUsername(), event.getEmail());
        UserProfile userProfileToSave = userProfileMapper.mapUserDataCreatedEventToUserProfile(event);
        userProfileRepository.save(userProfileToSave);
    }

    public void handleUserDataChangedEvent(UserDataChangedEvent event) {
        UserProfile userProfileToSave = getUserProfileOrThrow(event.getUserId());
        userProfileMapper.mapUserDataChangedEventToUserProfile(userProfileToSave, event);
        userProfileRepository.save(userProfileToSave);
    }

    @Transactional
    public void addProductToUserProfile(ProductDataCreatedEvent event) {
        UserProfile userProfile = getUserProfileOrThrow(event.getOwnerId());
        ProductInfo productInfo = productInfoMapper.mapPrductDataCreatedEventToProductInfo(event, userProfile);
        userProfile.getProducts().add(productInfo);
        userProfileRepository.save(userProfile);
        productInfoService.saveProductInfo(productInfo);
    }

    public void changeProductInfoForUserProfile(ProductDataChangedEvent event) {
        ProductInfo productInfo = productInfoService.getProductById(event.getProductId());

        getUserProfileOrThrow(productInfo.getUserProfile().getUserId());

        updateProductInfoFromEvent(productInfo, event);
        productInfoService.saveProductInfo(
                productInfoMapper.mapProductDataChangedEventToProductInfo(event, productInfo)
        );
    }

    public UserProfileDTO findUserProfile(FindUserProfileQuery query) {
        UserProfile userProfile = getUserProfileByUsernameOrThrow(query.getUsername());
        return userProfileMapper.mapUserProfileToUserProfileDTO(userProfile);
    }

    private UserProfile getUserProfileOrThrow(UUID userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new UserProfileNotExist("User profile not exist with id: " + userId));
    }

    private UserProfile getUserProfileByUsernameOrThrow(String username) {
        return userProfileRepository.findUserProfileByUsername(username)
                .orElseThrow(() -> new UserProfileNotExist("User profile not exist with username " + username));
    }

    private void updateProductInfoFromEvent(ProductInfo productInfo, ProductDataChangedEvent event) {
        productInfo.setBrand(event.getBrand());
        productInfo.setDescription(event.getDescription());
        productInfo.setName(event.getName());
        if (event.getCategory() != null) {
            productInfo.setCategory(event.getCategory().toString());
        }
        productInfo.setUnitsInStock(event.getUnitsInStock());
    }

    private void checkIfUserAlreadyExists(String username, String email) {
        if (userProfileRepository.findUserProfileByUsername(username).isPresent()) {
            throw new UserProfileAlreadyExist("User profile already exist with username: " + username +
                    " email: " + email);
        }
    }
}
