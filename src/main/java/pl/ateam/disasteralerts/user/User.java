package pl.ateam.disasteralerts.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ateam.disasteralerts.util.EntityAudit;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
class User extends EntityAudit {

    @Size(min = 5, max = 15)
    @Column(unique = true, length = 15)
    private String username;

    @Size(min = 5, max = 15)
    @Column(nullable = false)
    private String firstName;

    @Size(min = 2, max = 255)
    @Column(nullable = false)
    private String lastName;

    @NotBlank
    private String password;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(
            regexp = "^(\\+48)?\\d{9}$",
            message = "Nieprawidłowy format numeru telefonu"
    )
    private String phoneNumber;

    private String location;

    @Enumerated(EnumType.STRING)
    private Role role;

}