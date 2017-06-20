package com.comsysto.dcc.auth.config;

import com.comsysto.dcc.auth.EnhancedJwtAccessTokenConverter;
import com.comsysto.dcc.auth.client.CustomClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.resource.jwt.key-value}")
    private String jwtSigningKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomClientDetailsService clientsDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;


    //@Bean
    //public JwtAccessTokenConverter jwtAccessTokenConverter() {
    //    JwtAccessTokenConverter converter = new EnhancedJwtAccessTokenConverter(clientsDetailsService);
    //    converter.setSigningKey(jwtSigningKey);
    //    return converter;
    //}


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("keystore.jks"), "foobar".toCharArray())
                .getKeyPair("test");
        converter.setKeyPair(keyPair);
        return converter;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientsDetailsService);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(userDetailsService)
                 .authenticationManager(authenticationManager)
                 .accessTokenConverter(jwtAccessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                   .checkTokenAccess("isAuthenticated()");
    }

}
