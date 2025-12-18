package org.homemade.product.service.query.projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.homemade.common.event.UserDataCreatedEvent;
import org.homemade.common.event.orchestration.event.OwnerEmailUpdatedEvent;
import org.homemade.common.event.orchestration.event.UserEmailUpdatedEvent;
import org.homemade.product.service.service.OwnerService;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup( "owner-group")
public class OwnerProjection {

    private final OwnerService ownerService;

    public OwnerProjection(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @EventHandler
    public void on(UserDataCreatedEvent event){
        System.out.println("Handling UserDataCreatedEvent for: " + event.getEmail());
        System.out.println(event.toString());
        ownerService.createOwnerFromUserCreatedEvent(event);
    }

    @EventHandler
    public void on(OwnerEmailUpdatedEvent event){
        System.out.println("Handling UserEmailUpdatedEvent for owner: " + event.getOwnerId());
        System.out.println(event.toString());
        ownerService.updateOwnerEmail(event);
    }
}
