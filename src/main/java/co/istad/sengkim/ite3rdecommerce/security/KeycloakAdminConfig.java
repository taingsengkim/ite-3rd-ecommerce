package co.istad.sengkim.ite3rdecommerce.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdminConfig {
    private final KeycloakAdminProps keycloakAdminProps;
    // config this when we want to use Keycloak class
    @Bean
    public Keycloak keycloakAdminClient(){
        log.info("Keycloak Admin Client Props : {}" , keycloakAdminProps);
        return KeycloakBuilder.builder()
                .serverUrl(keycloakAdminProps.getServerUrl())
                .realm(keycloakAdminProps.getTargetRealm())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(keycloakAdminProps.getClientId())
                .clientSecret(keycloakAdminProps.getClientSecret())
                .build();
    }

}
