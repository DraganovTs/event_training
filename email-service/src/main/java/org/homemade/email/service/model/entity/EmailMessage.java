package org.homemade.email.service.model.entity;

import jakarta.persistence.*;
import lombok.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String recipient;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    private Date createdAt;

    private Date sentAt;

    @PrePersist
    public void onCreate() {
        createdAt = new Date();
    }
}
