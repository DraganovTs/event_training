package org.homemade.user.profile.service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {
    @Id
    @Column(name = "user_profile_id")
    private String userId;
    @NotBlank(message = "Username is required")
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastName;
    @NotBlank(message = "Phone is required")
    @Column(name = "phone", nullable = false)
    private String phone;
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductInfo> products;
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailsInfo> emails;
}
