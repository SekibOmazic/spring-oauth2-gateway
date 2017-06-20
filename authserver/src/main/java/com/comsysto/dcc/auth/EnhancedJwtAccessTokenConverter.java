package com.comsysto.dcc.auth;

import com.comsysto.dcc.auth.client.CustomClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class EnhancedJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private CustomClientDetailsService clientsDetailsService;

    @Autowired
    public EnhancedJwtAccessTokenConverter(CustomClientDetailsService clientsDetailsService) {
        this.clientsDetailsService = clientsDetailsService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        ClientDetails clientDetails = clientsDetailsService
                .loadClientByClientId(authentication.getOAuth2Request().getClientId());

        if (accessToken instanceof DefaultOAuth2AccessToken) {
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(clientDetails.getAdditionalInformation());
        }

        OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);

        return enhancedToken;
    }
}
