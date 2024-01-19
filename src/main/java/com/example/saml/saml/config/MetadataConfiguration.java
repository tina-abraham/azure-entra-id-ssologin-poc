package com.example.saml.saml.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
//import org.springframework.security.saml2.provider.service.metadata.Saml2ServiceProviderMetadataConfiguration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;

@Configuration
@Order(1)
@ConfigurationProperties(prefix = "spring.security.saml2")
public class MetadataConfiguration {

    @Value("${spring.security.saml2.service-provider.providers.metadata:https://login.microsoftonline.com/b46c8501-fe2f-4e49-98a9-6a8a811e4f39/federationmetadata/2007-06/federationmetadata.xml?appid=cfaeb052-fbf1-4203-93eb-714a868ed88d}")
    private String metadataUrl;

    @Value("${saml2.rp.registration-id:ClmTest}")
    private String registrationId;

    // //@Value("${saml2.rp.local-entity-id:https://sts.windows.net/b46c8501-fe2f-4e49-98a9-6a8a811e4f39/}")
    @Value("${saml2.rp.local-entity-id:ClmTest}")
    private String localEntityId;

    @Value("${saml2.rp.acs-url:https://localhost:8443/logged-in/}")
    private String acsUrl;

    public void setMetadataUrl(String metadataUrl) {
        this.metadataUrl = metadataUrl;
    }

    @Bean
    public RelyingPartyRegistrationRepository relyingPartyRegistrationRepository() {
            RelyingPartyRegistration registration = RelyingPartyRegistrations
                .fromMetadataLocation(metadataUrl)
                .registrationId(registrationId)
               // .entityId(localEntityId)
               .assertionConsumerServiceLocation(acsUrl)
                .build();
        System.out.println("registration: " + registration.getRegistrationId());
        return new InMemoryRelyingPartyRegistrationRepository(registration);
    }
    
}
