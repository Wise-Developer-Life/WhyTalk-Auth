package com.wisedevlife.whytalkauth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String email;

    private LocalDateTime lastLoginTime;

    @CreatedDate private LocalDateTime createdAt;

    @LastModifiedDate private LocalDateTime updatedAt;

    public static User ofCreate(String email) {
        return new User().setEmail(email).setLastLoginTime(LocalDateTime.now());
    }
}
