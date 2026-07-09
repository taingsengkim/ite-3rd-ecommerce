package co.istad.sengkim.ite3rdecommerce.features.userprofile;

import co.istad.sengkim.ite3rdecommerce.features.userprofile.dto.UserProfileResponse;
import org.springframework.stereotype.Service;

public interface UserProfileService {
    UserProfileResponse me();
}
