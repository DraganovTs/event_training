package org.homemade.user.profile.service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "emails_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailsInfo {
    @Id
    @Column(name = "email_info_id")
    private UUID emailId;
    @NotNull(message = "Recipient must not be null")
    @Column(name = "email_user_id")
    private String recipient;
    @NotNull(message = "Subject must not be null")
    @Column(name = "subject")
    private String subject;
    @NotNull(message = "Body must not be null")
    @Column(columnDefinition = "TEXT")
    private String body;
    @NotNull(message = "Status must not be null")
    @Column(name = "status")
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;
}
