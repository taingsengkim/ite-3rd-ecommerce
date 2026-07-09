package co.istad.sengkim.ite3rdecommerce.features.userprofile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    private String userId;
    private String gender;
    private String biography;
    private String address;
    private String profilePicture;
}
