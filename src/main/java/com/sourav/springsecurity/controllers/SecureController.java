package com.sourav.springsecurity.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecureController {

    @GetMapping("/callback/")
    public String main(OAuth2AuthenticationToken token) {
        System.out.println(token.getPrincipal());
        return "index";
    }

    /*private ClientRegistration clientRegistration() {
		return CommonOAuth2Provider.GITHUB.getBuilder("github").clientId("42d799aafaa59eee70bf")
	           .clientSecret("911ac4de96a198377ad2dd94f51707177350683d").build();
	 }*/


    /*
     * private ClientRegistration clientRegistration() { ClientRegistration cr =
     * ClientRegistration.withRegistrationId("github").clientId(
     * "3c9be97074f067e78e75")
     * .clientSecret("ab313f7ade3d79e06c192ca80cf152c43cb5d916").scope(new String[]
     * { "read:user" })
     * .authorizationUri("https://github.com/login/oauth/authorize")
     * .tokenUri("https://github.com/login/oauth/access_token").userInfoUri(
     * "https://api.github.com/user")
     * .userNameAttributeName("id").clientName("GitHub")
     * .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
     * .redirectUriTemplate("{baseUrl}/{action}/oauth2/code/{registrationId}").build
     * (); return cr; }
     */


    /*
     * @Bean public ClientRegistrationRepository clientRepository() {
     * ClientRegistration clientReg = clientRegistration(); return new
     * InMemoryClientRegistrationRepository(clientReg); }
     */

}
