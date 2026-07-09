package co.istad.sengkim.ite3rdecommerce.features.auth;

import co.istad.sengkim.ite3rdecommerce.features.auth.dto.RegisterRequest;
import co.istad.sengkim.ite3rdecommerce.features.auth.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
}
