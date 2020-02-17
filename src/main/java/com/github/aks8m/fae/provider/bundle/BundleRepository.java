package com.github.aks8m.fae.provider.bundle;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BundleRepository extends MongoRepository<Bundle, String> {

    Bundle findBundleById(IdType idType);
    long deleteBundleById(IdType idType);
}
