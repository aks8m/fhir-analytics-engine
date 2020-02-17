package com.github.aks8m.fae.provider.immunization;

import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import com.github.aks8m.fae.LoggerStrings;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Immunization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImmunizationService {

    private static Logger logger = LoggerFactory.getLogger(ImmunizationService.class);
    private final String resourceNameForLog = "Immunization";
    private final String resourceNotFoundExceptionString = "Immunization resource not found, id: ";

    @Autowired
    private ImmunizationRepository repository;

    public Immunization post(Immunization resource){

        String id = UUID.randomUUID().toString();
        resource.setId(id);

        this.repository.insert(resource);

        logger.info(LoggerStrings.POST_SUCCESS_WITH_ID, "Immunization", id);

        return resource;
    }

    public Immunization get(String id){

        Optional<Immunization> optionalImmunization = Optional.ofNullable(repository.findImmunizationById(new IdType(id)));

        if(optionalImmunization.isEmpty()) {

            logger.error(LoggerStrings.GET_FAIL_WITH_ID, this.resourceNameForLog, id);
            throw new ResourceNotFoundException(this.resourceNotFoundExceptionString + id);
        }

        logger.info(LoggerStrings.GET_SUCCESS_WITH_ID, this.resourceNameForLog, id);

        return optionalImmunization.get();
    }

    public Immunization put(Immunization resource){

        Optional<Immunization> optionalResource = Optional.ofNullable(repository.save(resource));

        if(optionalResource.isEmpty()) {

            logger.error(LoggerStrings.PUT_FAIL_WITH_ID, this.resourceNameForLog, resource.getId());
            throw new ResourceNotFoundException(this.resourceNotFoundExceptionString + resource.getId());
        }

        logger.info(LoggerStrings.PUT_SUCCESS_WITH_ID, this.resourceNameForLog, resource.getId());

        return optionalResource.get();
    }

    public void delete(String id){

        long deleteCount = this.repository.deleteImmunizationById(new IdType(id));

        if(deleteCount == 0){

            logger.info(LoggerStrings.DELETE_FAIL_WITH_ID, this.resourceNameForLog, id);
            throw new ResourceNotFoundException(this.resourceNotFoundExceptionString + id);
        }

        logger.info(LoggerStrings.DELETE_SUCCESS_WITH_ID, this.resourceNameForLog, id);
    }

    public List<Immunization> search(){
        List<Immunization> searchResults = repository.findAll();

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
