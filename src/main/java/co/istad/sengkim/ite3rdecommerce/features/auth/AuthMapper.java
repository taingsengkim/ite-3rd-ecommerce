package co.istad.sengkim.ite3rdecommerce.features.auth;

import co.istad.sengkim.ite3rdecommerce.features.auth.dto.RegisterResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AuthMapper {
    public RegisterResponse mapUserRepresentationToRegisterResponse(UserRepresentation userRepresentation){
        return RegisterResponse.builder()
                .username(userRepresentation.getUsername())
                .userId(userRepresentation.getId())
                .phoneNumber(userRepresentation.getAttributes().get("phoneNumber").getFirst())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .build();
    }
}
