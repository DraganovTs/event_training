package org.homemade.user.service.saga;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.spring.stereotype.Saga;
import org.homemade.common.event.choreography.event.UserEmailUpdatedEvent;

@Saga
@Slf4j
public class UpdateUserEmailSaga {

    @SagaEventHandler(associationProperty = "userId")
    public void handle(UserEmailUpdatedEvent event){

    }
}
