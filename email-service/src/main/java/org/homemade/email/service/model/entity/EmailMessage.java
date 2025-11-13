package org.homemade.email.service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.homemade.email.service.model.enums.EmailStatus;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "emails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailMessage {

    @Id
    @Column(name = "email_message_id")
    private UUID id;
    @Email
    @Column(name = "recipient")
    private String recipient;
    @NotBlank(message = "Subject must not be blank")
    @Size(max = 100, message = "Subject must be at most 100 characters")
    @Column(name = "subject", nullable = false)
    private String subject;
    @NotBlank(message = "Body must not be blank")
    @Size(max = 1000, message = "Body must be at most 1000 characters")
    @Column(columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "sent_at")
    @CreationTimestamp
    private Date sentAt;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email_user_id")
    private EmailUser emailUser;

    @PrePersist
    public void onCreate() {
        createdAt = new Date();
        sentAt = new Date();
    }
}
