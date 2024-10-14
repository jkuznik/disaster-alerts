package pl.ateam.disasteralerts.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
class User {

    @Id
    @GeneratedValue
    private UUID id;

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

    private Role role;
}