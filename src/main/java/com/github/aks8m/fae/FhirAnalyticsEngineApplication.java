package com.github.aks8m.fae;

import com.github.aks8m.fae.provider.immunization.ImmunizationProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FhirAnalyticsEngineApplication extends ResourceConfig {

	public FhirAnalyticsEngineApplication() {
		register(ImmunizationProvider.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FhirAnalyticsEngineApplication.class, args);
	}
}
