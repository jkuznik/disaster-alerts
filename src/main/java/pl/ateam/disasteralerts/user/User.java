package pl.ateam.disasteralerts.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
class User {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
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

    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate createDate;

    private LocalDate updateDate;

    @PrePersist
    public void setCreateDate() {
        this.createDate = LocalDate.now();
    }

    @PreUpdate
    public void setUpdateDate() {
        this.updateDate = LocalDate.now();
    }
}