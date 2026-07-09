package co.istad.sengkim.ite3rdecommerce.features.auth;

import co.istad.sengkim.ite3rdecommerce.features.auth.dto.RegisterRequest;
import co.istad.sengkim.ite3rdecommerce.features.auth.dto.RegisterResponse;
import co.istad.sengkim.ite3rdecommerce.features.userprofile.UserProfile;
import co.istad.sengkim.ite3rdecommerce.features.userprofile.UserProfileMapper;
import co.istad.sengkim.ite3rdecommerce.features.userprofile.UserProfileRepository;
import co.istad.sengkim.ite3rdecommerce.security.KeycloakAdminProps;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Keycloak keycloak;
    private final KeycloakAdminProps props;
    private final AuthMapper authMapper;
    private final UserProfileRepository userProfileRepository;
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(!request.password().equals(request.confirmPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password doesn't match!");
        }
        UsersResource userResource = keycloak.realm(props.getTargetRealm()).users();
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.username());
        userRepresentation.setEmail(request.email());
        userRepresentation.setFirstName(request.firstName());
        userRepresentation.setLastName(request.lastName());
        //Set keycloak custom attributes
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("phoneNumber",List.of(request.phoneNumber()));
        userRepresentation.setAttributes(attributes);

        //Set Credential
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());

        userRepresentation.setEnabled(true); // allow to create password
        userRepresentation.setEmailVerified(false);
        userRepresentation.setCredentials(List.of(credential));//set password to kc


        //try to create something on keycloak
        try(Response response = userResource.create(userRepresentation)) {
            log.info("Response status code : {}" , response.getStatus());
            if(response.getStatus() == HttpStatus.CREATED.value()){
                //success situation
                UserRepresentation createdUser = keycloak.realm(props.getTargetRealm()).users()
                        .search(userRepresentation.getUsername())
                        .getFirst();
                log.info("Created user : {}", createdUser);

                UserResource userResourceSet = keycloak.realm(props.getTargetRealm())
                        .users().get(createdUser.getId());
                userResourceSet.sendVerifyEmail();

                RoleRepresentation roleUser = keycloak.realm(props.getTargetRealm())
                        .roles().get(RoleEnum.USER.name()).toRepresentation();
                userResourceSet.roles().realmLevel().add(List.of(roleUser));
                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(createdUser.getId());
                userProfileRepository.save(userProfile);
                return authMapper.mapUserRepresentationToRegisterResponse(createdUser);
            }else if (response.getStatus() == HttpStatus.CONFLICT.value()){
                //conflict situation
                log.info("Check username or email already exist");
            }
        }

        return null;
    }
}
