package org.homemade.user.profile.service.service;

import jakarta.transaction.Transactional;
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

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final ProductInfoMapper productInfoMapper;
    private final ProductInfoService productInfoService;

    public UserProfileService(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper,
                              ProductInfoMapper productInfoMapper, ProductInfoService productInfoService) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
        this.productInfoMapper = productInfoMapper;
        this.productInfoService = productInfoService;
    }

    public void handleUserDataCreatedEvent(UserDataCreatedEvent event) {
        checkIfUserExistByUsernameAndEmail(event.getUsername(), event.getEmail());
        UserProfile userProfileToSave = userProfileMapper.mapUserDataCreatedEventToUserProfile(event);
        userProfileRepository.save(userProfileToSave);
    }

    public void handleUserDataChangedEvent(UserDataChangedEvent event) {
        checkIfUserExistByUserId(event.getUserId());
        UserProfile userProfileToSave = userProfileRepository.findById(event.getUserId()).get();
        userProfileMapper.mapUserDataChangedEventToUserProfile(userProfileToSave, event);
        userProfileRepository.save(userProfileToSave);
    }

    @Transactional
    public void addProductToUserProfile(ProductDataCreatedEvent event) {
        checkIfUserExistByUserId(event.getOwnerId());
        UserProfile userProfile = userProfileRepository.findById(event.getOwnerId()).get();
        ProductInfo productInfo = productInfoMapper.mapPrductDataCreatedEventToProductInfo(event, userProfile);
        userProfile.getProducts().add(productInfo);
        userProfileRepository.save(userProfile);
        productInfoService.saveProductInfo(productInfo);
    }

    public void changeProductInfoForUserProfile(ProductDataChangedEvent event) {
        ProductInfo productInfo = productInfoService.getProductById(event.getProductId());
        checkIfUserExistByUserId(productInfo.getUserProfile().getUserId());

        UserProfile userProfile = userProfileRepository.findById(productInfo.getUserProfile().getUserId()).get();

        userProfile.getProducts().stream()
                .filter(product -> product.getProductId().equals(event.getProductId()))
                .findFirst()
                .ifPresent(product -> updateProductInfoFromEvent(product, event));

        productInfoService.saveProductInfo(
                productInfoMapper.mapProductDataChangedEventToProductInfo(event, productInfo)
        );
    }

    private void updateProductInfoFromEvent(ProductInfo productInfo, ProductDataChangedEvent event) {
        productInfo.setBrand(event.getBrand());
        productInfo.setDescription(event.getDescription());
        productInfo.setName(event.getName());
        productInfo.setCategory(event.getCategory().toString());
        productInfo.setUnitsInStock(event.getUnitsInStock());
    }
    private void checkIfUserExistByUserId(UUID userId) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findById(userId);
        if (optionalUserProfile.isEmpty()) {
            throw new UserProfileNotExist("User profile not exist whit id: " + userId);
        }
    }


    public UserProfileDTO findUserProfile(FindUserProfileQuery query) {
        checkIfUserExistByUserName(query.getUsername());
        UserProfile userProfile = userProfileRepository.findUserProfileByUsername(query.getUsername()).get();
        return userProfileMapper.mapUserProfileToUserProfileDTO(userProfile);
    }

    private void checkIfUserExistByUserName(String username) {
        Optional<UserProfile> optionalUserProfileByUsername = userProfileRepository.findUserProfileByUsername(username);
        if (optionalUserProfileByUsername.isEmpty()) {
            throw new UserProfileNotExist("User profile not exist whit username " + username);
        }
    }


    private void checkIfUserExistByUsernameAndEmail(String username, String email) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findUserProfileByUsername(email);
        if (optionalUserProfile.isPresent()) {
            throw new UserProfileAlreadyExist("User profile already exist whit username: " + username +
                    "email: " + email);
        }
    }


}
