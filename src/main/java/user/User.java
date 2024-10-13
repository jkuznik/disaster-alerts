package user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user")
class User {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private String username;
    private String password;
    private String email;
}