package org.homemade.product.service.service;


import org.homemade.common.event.UserDataCreatedEvent;
import org.homemade.common.event.orchestration.event.OwnerEmailUpdatedEvent;
import org.homemade.common.model.dto.OwnerDTO;
import org.homemade.product.service.exception.OwnerAlreadyExistsException;
import org.homemade.product.service.exception.OwnerNotFoundException;
import org.homemade.product.service.mapper.ProductServiceMapper;
import org.homemade.product.service.model.entity.Owner;
import org.homemade.product.service.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final ProductServiceMapper mapper;

    public OwnerService(OwnerRepository ownerRepository, ProductServiceMapper mapper) {
        this.ownerRepository = ownerRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Owner getOwnerById(UUID ownerId) {
        return ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException(
                "owner not found whit id: " + ownerId
        ));
    }

    @Transactional
    public OwnerDTO createOwner(OwnerDTO request) {
        assertOwnerEmailDoesNotExist(request.getOwnerEmail());
        Owner savedOwner = ownerRepository.save(mapper.mapOwnerDTOToOwner(request));
        return mapper.mapOwnerToOwnerDTO(savedOwner);
    }

    @Transactional
    public void createOwnerFromUserCreatedEvent(UserDataCreatedEvent event) {
        assertOwnerEmailDoesNotExist(event.getEmail());
        ownerRepository.save(mapper.mapUserDataCreatedEventToOwner(event));
    }

    @Transactional
    public void updateOwnerEmail(OwnerEmailUpdatedEvent event) {
        assertOwnerEmailExists(event.getOwnerEmail());

        Owner ownerToUpdate = getOwnerById(event.getOwnerId());
        ownerToUpdate.setOwnerEmail(event.getNewOwnerEmail());

        ownerRepository.save(ownerToUpdate);
    }

    private void assertOwnerEmailExists(String ownerEmail) {
        if (!ownerRepository.existsByOwnerEmail(ownerEmail)) {
            throw new OwnerNotFoundException("Owner not found whit id " + ownerEmail);
        }
    }

    private void assertOwnerEmailDoesNotExist(String ownerEmail) {
        if (ownerRepository.existsByOwnerEmail(ownerEmail)) {
            throw new OwnerAlreadyExistsException("Owner already exist whit id " + ownerEmail);
        }
    }

}
