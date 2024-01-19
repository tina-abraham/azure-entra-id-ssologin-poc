package com.example.saml.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	// private final RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

    // @Autowired
    // public IndexController(RelyingPartyRegistrationRepository relyingPartyRegistrationRepository) {
    //     this.relyingPartyRegistrationRepository = relyingPartyRegistrationRepository;
    // }
	@Value("${saml.discovery.url:/saml/sp/discovery}")
	private String samlDiscoveryUrl;

	@Value("${saml.discovery.entity-id:https://sts.windows.net/b46c8501-fe2f-4e49-98a9-6a8a811e4f39/}")
	private String samlDiscoveryEntityId;

    @RequestMapping("/home")
    public String home(Model model) {
        //RelyingPartyRegistration registration = relyingPartyRegistrationRepository.findByRegistrationId("ClmTest");

        // if (registration != null) {
		// String loginUrl = registration.getAssertionConsumerServiceLocation();
		// System.out.println("loginUrl: " + loginUrl);
		// model.addAttribute("samlLink", "0; url='" + loginUrl + "'");
        // }
		//model.addAttribute("samlLink", "0; url='" + loginUrl + "'");
		model.addAttribute("samlLink", "0; url='"+ samlDiscoveryUrl + "?idp="+ samlDiscoveryEntityId+"'");
        System.out.println("In home");
		return "saml-login";
    }

	@RequestMapping(value = {"/"})
	public String home(){
		return "index";
	}

	@RequestMapping(value = {"/main"})
	public String main(){
		System.out.println("In main");
		return "main";
	}

	@RequestMapping(value = {"/logged-in"})
	public String loggedin() {
		System.out.println("In login");
		return "logged-in";
	}

	@RequestMapping(value = {"/log-out"})
	public String logout() {
		System.out.println("In logout");
		return "logged-out";
	}

}
