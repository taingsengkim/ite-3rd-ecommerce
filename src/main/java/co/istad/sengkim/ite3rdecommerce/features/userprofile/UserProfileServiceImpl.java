package co.istad.sengkim.ite3rdecommerce.features.userprofile;


import co.istad.sengkim.ite3rdecommerce.features.userprofile.dto.UserProfileResponse;
import co.istad.sengkim.ite3rdecommerce.security.AuthUtils;
import co.istad.sengkim.ite3rdecommerce.security.KeycloakAdminProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final Keycloak keycloak; // we must config keycloak admin client first
    private final KeycloakAdminProps props;
    private final UserProfileMapper userProfileMapper;
    @Override
    public UserProfileResponse me() {
        String userId = AuthUtils.extractUserId();
        UserRepresentation userRepresentation=keycloak.realm(props.getTargetRealm())
                .users()
                .get(userId)
                .toRepresentation(); // calling keycloak admin api
        return userProfileMapper.mapUserRepresentationToUserProfileResponse(userRepresentation);
    }
}
