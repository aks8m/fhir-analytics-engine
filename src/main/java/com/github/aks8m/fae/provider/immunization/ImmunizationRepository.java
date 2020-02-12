package com.github.aks8m.fae.provider.immunization;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Immunization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ImmunizationRepository extends MongoRepository<Immunization, String> {

    Immunization findImmunizationById(IdType idType);

    List<Immunization> findAll();

}
