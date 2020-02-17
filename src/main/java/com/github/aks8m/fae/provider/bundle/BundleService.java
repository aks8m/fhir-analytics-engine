package com.github.aks8m.fae.provider.bundle;

import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import com.github.aks8m.fae.LoggerStrings;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BundleService {

    private static Logger logger = LoggerFactory.getLogger(BundleService.class);
    private final String resourceNameForLog = "Bundle";
    private final String resourceNotFoundExceptionString = "Bundle resource not found, id: ";

    @Autowired
    private BundleRepository repository;

    public Bundle post(Bundle resource){

        String id = UUID.randomUUID().toString();
        resource.setId(id);

        this.repository.insert(resource);

        logger.info(LoggerStrings.POST_SUCCESS_WITH_ID, this.resourceNameForLog, id);

        return resource;
    }

    public Bundle get(String id){

        Optional<Bundle> optionalImmunization = Optional.ofNullable(repository.findBundleById(new IdType(id)));

        if(optionalImmunization.isEmpty()) {

            logger.error(LoggerStrings.GET_FAIL_WITH_ID, this.resourceNameForLog, id);
            throw new ResourceNotFoundException(this.resourceNotFoundExceptionString + id);
        }

        logger.info(LoggerStrings.GET_SUCCESS_WITH_ID, this.resourceNameForLog, id);

        return optionalImmunization.get();
    }

    public Bundle put(Bundle resource){

        Optional<Bundle> optionalResource = Optional.ofNullable(repository.save(resource));

        if(optionalResource.isEmpty()) {

            logger.error(LoggerStrings.PUT_FAIL_WITH_ID, this.resourceNameForLog, resource.getId());
            throw new ResourceNotFoundException(this.resourceNotFoundExceptionString + resource.getId());
        }

        logger.info(LoggerStrings.PUT_SUCCESS_WITH_ID, this.resourceNameForLog, resource.getId());

        return optionalResource.get();
    }

    public void delete(String id){

        long deleteCount = this.repository.deleteBundleById(new IdType(id));

        if(deleteCount == 0){

            logger.info(LoggerStrings.DELETE_FAIL_WITH_ID, this.resourceNameForLog, id);
            throw new ResourceNotFoundException(this.resourceNotFoundExceptionString + id);
        }

        logger.info(LoggerStrings.DELETE_SUCCESS_WITH_ID, this.resourceNameForLog, id);
    }

    public List<Bundle> search(){
        List<Bundle> searchResults = repository.findAll();

        if(searchResults.size() == 0){

            logger.info(LoggerStrings.SEARCH_FAIL_WITH_PARAMS, this.resourceNameForLog, "all");
        }

        searchResults.stream()
                .forEach(immunization ->
                        logger.info(LoggerStrings.SEARCH_SUCCESS_WITH_ID, this.resourceNameForLog, immunization.getId())
                );

        return searchResults;
    }
}
