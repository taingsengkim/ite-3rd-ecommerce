package co.istad.sengkim.ite3rdecommerce.features.userprofile.dto;

import lombok.Builder;

@Builder
public record UserProfileResponse(
        String userId,
        String username,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String gender,
        String address,
        String biography,
        String profilePicture
) {
}
