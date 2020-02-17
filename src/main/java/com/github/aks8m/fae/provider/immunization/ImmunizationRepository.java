package com.github.aks8m.fae.provider.immunization;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Immunization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmunizationRepository extends MongoRepository<Immunization, String> {

    Immunization findImmunizationById(IdType idType);
    long deleteImmunizationById(IdType idType);
}
