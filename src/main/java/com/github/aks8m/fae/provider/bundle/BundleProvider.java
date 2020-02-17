package com.github.aks8m.fae.provider.bundle;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jaxrs.server.AbstractJaxRsResourceProvider;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BundleProvider extends AbstractJaxRsResourceProvider<Bundle> {

    private static Logger logger = LoggerFactory.getLogger(BundleProvider.class);

    public BundleProvider(FhirContext ctx) {
        super(ctx);
    }

    @Override
    public Class<Bundle> getResourceType() {
        return Bundle.class;
    }

    @Autowired
    BundleService service;
    @Autowired
    TransactionService transactionService;

    @Create
    public MethodOutcome createResource(@ResourceParam Bundle resource){

        return new MethodOutcome()
                .setCreated(true)
                .setResource(service.post(resource))
                .setOperationOutcome(new OperationOutcome());
    }

    @Read
    public Bundle readResource(@IdParam IdType id){

        return this.service.get(id.getIdPart());
    }

    @Update
    public MethodOutcome updateResource(@IdParam IdType id, @ResourceParam Bundle resource) {

        resource.setId(new IdType(id.getIdPart()));
        return new MethodOutcome()
                .setResource(this.service.put(resource))
                .setOperationOutcome(new OperationOutcome());
    }

    @Delete
    public void deleteResource(@IdParam IdType id) {

        this.service.delete(id.getIdPart());
    }

    @Search
    public List<Bundle> searchResource() {

        return this.service.search();
    }

    @Transaction
    public Bundle transaction(@TransactionParam Bundle theInput) {
        return this.transactionService.processTransaction(theInput);
    }
}
