package com.comsysto.dcc.auth.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomClientDetailsService implements ClientDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return mapClientInfo(clientRepository.findOne(clientId));
    }

    private ClientDetails mapClientInfo(Client client) {
        BaseClientDetails baseClientDetails =
                new BaseClientDetails(client.getId(), null, client.getScope(), client.getAuthorizedGrantTypes(), "ACTUATOR");

        baseClientDetails.setAccessTokenValiditySeconds(1800); // 30min

        baseClientDetails.setClientSecret(client.getSecret());

        // Used to disable CSRF and other "annoying" Stuff when used by R / simple clients
        baseClientDetails.addAdditionalInformation("serviceAccount", client.isAutoApproveScopes());

        return baseClientDetails;
    }
}
