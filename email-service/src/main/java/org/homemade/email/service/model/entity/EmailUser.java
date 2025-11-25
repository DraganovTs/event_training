package org.homemade.email.service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "email_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailUser {
    @Id
    @Column(name = "email_user_id")
    private UUID emailUserId;
    @NotBlank(message = "Owner name must not be blank")
    @Size(max = 30, message = "Owner name must be at most 30 characters")
    @Column(name = "owner_name")
    private String ownerName;
    @Email(message = "Owner email should be valid")
    @NotBlank(message = "Owner email must not be blank")
    @Size(max = 30, message = "Owner email must be at most 30 characters")
    @Column(name = "owner_email")
    private String ownerEmail;
    @OneToMany(mappedBy = "emailUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailMessage> emails;
}
