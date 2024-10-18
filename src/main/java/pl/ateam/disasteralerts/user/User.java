package pl.ateam.disasteralerts.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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

    private String password;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 9, max = 9)
    private String phoneNumber;

    private String location;

    @Enumerated(EnumType.STRING)
    private Role role;

}