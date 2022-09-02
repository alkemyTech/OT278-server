package com.alkemy.ong.security.model;

<<<<<<< HEAD

=======
>>>>>>> develop
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "FirstName cannot be null.")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "LastName cannot be null.")
    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    @NotNull(message = "Email cannot be null.")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null.")
    private String password;

    private String photo;

    @ManyToOne
    private Role role;

    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;


}
