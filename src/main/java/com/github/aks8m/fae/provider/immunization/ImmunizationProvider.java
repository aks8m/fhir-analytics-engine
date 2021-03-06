package com.github.aks8m.fae.provider.immunization;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jaxrs.server.AbstractJaxRsResourceProvider;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImmunizationProvider extends AbstractJaxRsResourceProvider<Immunization> {

    private static Logger logger = LoggerFactory.getLogger(ImmunizationProvider.class);

    @Autowired
    ImmunizationService service;

    public ImmunizationProvider(FhirContext ctx) {
        super(ctx);
    }

    @Override
    public Class<Immunization> getResourceType() {
        return Immunization.class;
    }

    @Create
    public MethodOutcome createResource(@ResourceParam Immunization resource){

        return new MethodOutcome()
                .setCreated(true)
                .setResource(service.post(resource))
                .setOperationOutcome(new OperationOutcome());
    }

    @Read
    public Immunization readResource(@IdParam IdType id){

        return this.service.get(id.getIdPart());
    }

    @Update
    public MethodOutcome updateResource(@IdParam IdType id, @ResourceParam Immunization resource) {

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
    public List<Immunization> searchResource() {

        return this.service.search();
    }
}
