package com.github.aks8m.fae.provider.immunization;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jaxrs.server.AbstractJaxRsResourceProvider;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Immunization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ImmunizationProvider extends AbstractJaxRsResourceProvider<Immunization> {

    private static Logger logger = LoggerFactory.getLogger(ImmunizationProvider.class);

    @Autowired
    ImmunizationRepository immunizationRepository;

    public ImmunizationProvider(FhirContext ctx) {
        super(ctx);
    }

    @Override
    public Class<Immunization> getResourceType() {
        return Immunization.class;
    }

    @Create
    public MethodOutcome createImmunization(@ResourceParam Immunization immunization){

        immunization.setId(UUID.randomUUID().toString());
        immunizationRepository.insert(immunization);

        return new MethodOutcome().setCreated(true);
    }

    @Read
    public Immunization getImmunization(@IdParam IdType theId){
        return immunizationRepository.findImmunizationById(new IdType(theId.getIdPart()));
    }
}
