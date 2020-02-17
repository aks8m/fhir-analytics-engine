package com.github.aks8m.fae.provider.bundle;

import com.github.aks8m.fae.provider.immunization.ImmunizationService;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    BundleService bundleService;
    @Autowired
    ImmunizationService immunizationService;

    public Bundle processTransaction(Bundle transactionBundle){
        Bundle transactionResultsBundle = new Bundle();

        transactionBundle.getEntry().stream()
                .forEach(bundleEntryComponent -> {

                    if(bundleEntryComponent.hasResource() && bundleEntryComponent.hasRequest()) {
                        Resource resource = bundleEntryComponent.getResource();

                        switch (bundleEntryComponent.getRequest().getMethod()) {

                            case POST:
                                if(resource instanceof Immunization){
                                    this.immunizationService.post((Immunization) resource);
                                }else if(resource instanceof Bundle){
                                    this.bundleService.post((Bundle) resource);
                                }
                                break;

                            case GET:
                                break;

                            case PUT:
                                break;

                            case DELETE:
                                break;

                            default:
                                break;
                        }
                    }
                });

        return transactionResultsBundle;
    }


}
