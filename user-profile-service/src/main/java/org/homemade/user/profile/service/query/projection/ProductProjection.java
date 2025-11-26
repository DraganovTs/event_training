package org.homemade.user.profile.service.query.projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.homemade.common.event.ProductDataChangedEvent;
import org.homemade.common.event.ProductDataCreatedEvent;
import org.homemade.user.profile.service.service.UserProfileService;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup( "product-group")
public class ProductProjection {

    private final UserProfileService userProfileService;

    public ProductProjection(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    @EventHandler
    public void on(ProductDataCreatedEvent event){
        System.out.println("Handling ProductDataCreatedEvent for product" + event.getName());
        System.out.println(event.toString());
        userProfileService.addProductToUserProfile(event);
    }

    @EventHandler
    public void on(ProductDataChangedEvent event){
        System.out.println("Handling ProductDataChangedEvent for product" + event.getName());
        System.out.println(event.toString());
        userProfileService.changeProductInfoForUserProfile(event);
    }
}
