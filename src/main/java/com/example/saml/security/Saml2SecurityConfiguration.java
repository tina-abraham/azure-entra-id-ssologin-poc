package com.example.saml.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;

@EnableWebSecurity
@Configuration
@Order(2)
public class Saml2SecurityConfiguration {

    // @Autowired
    // private RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

    @Autowired
    private RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

    // @Bean
    // public RelyingPartyRegistrationRepository relyingPartyRegistrationRepository() {
    //     RelyingPartyRegistration relyingPartyRegistration = RelyingPartyRegistrations
    //         .fromMetadataLocation("https://login.microsoftonline.com/b46c8501-fe2f-4e49-98a9-6a8a811e4f39/federationmetadata/2007-06/federationmetadata.xml?appid=cfaeb052-fbf1-4203-93eb-714a868ed88d")
    //         .registrationId("ClmTest")
    //         //.entityId("ClmTest")
    //         .assertionConsumerServiceLocation("https://localhost:8443/logged-in/")
    //         .build();
    //     String loginUrl = relyingPartyRegistration.getAssertionConsumerServiceLocation(); // Get the login URL from the registration
	// 	System.out.println("loginUrl*****: " + loginUrl);
    //     return new InMemoryRelyingPartyRegistrationRepository(relyingPartyRegistration);
    // }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
        .requestMatchers("/resources/**"); // Adjust ignored paths as needed
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // RelyingPartyRegistrationRepository relyingPartyRegistrationRepository = new
        // InMemoryRelyingPartyRegistrationRepository(relyingPartyRegistration);

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/home","/api/samlLink").permitAll()
                        .anyRequest().authenticated())
                .saml2Login(saml2 -> saml2
                .relyingPartyRegistrationRepository(relyingPartyRegistrationRepository)
                )
                .saml2Metadata((saml2) -> saml2.metadataUrl("classpath:/metadata/azure-idp.xml"))
                .formLogin(formLogin -> formLogin
                        .loginPage("/home")
                        .permitAll() // You may need to permit all to access the login page
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/log-out")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .cacheControl(cache -> cache.disable()));
        return http.build();
    }

    
    
}
