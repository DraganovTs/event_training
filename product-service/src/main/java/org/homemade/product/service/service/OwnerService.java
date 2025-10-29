package org.homemade.product.service.service;


import org.homemade.product.service.exception.OwnerAlreadyExistsException;
import org.homemade.product.service.exception.OwnerNotFoundException;
import org.homemade.product.service.mapper.ProductServiceMapper;
import org.homemade.product.service.model.dto.OwnerDTO;
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


    public Owner getOwnerById(UUID ownerId) {
        return ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException(
                "owner not found whit id: " + ownerId
        ));
    }

    @Transactional
    public OwnerDTO createOwner(OwnerDTO request) {

        checkIfOwnerExist(request.getOwnerEmail());

        Owner ownerToSave = mapper.mapOwnerDTOToOwner(request);

        Owner savedOwner = ownerRepository.save(ownerToSave);

        return mapper.mapOwnerToOwnerDTO(savedOwner);

    }

    @Transactional(readOnly = true)
    public void checkIfOwnerExist(String ownerEmail) {
        if (ownerRepository.existsByOwnerEmail(ownerEmail)) {
            throw new OwnerAlreadyExistsException("Owner already exists: " + ownerEmail);
        }
    }
}
