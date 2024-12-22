package com.tekarch.user_managementMS.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private List<Account> accounts;

   @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
   private PersonalInfo personalInfo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")  // Explicitly specify the database column name
    private Long userId;

    @Column(name = "user_name", nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, unique = true, columnDefinition = "TEXT")
    private String password_hash;

    @Column( unique = true, length = 15)
    private String phone_number;

    @Column(name = "two_factor_enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean two_factor_enabled =Boolean.FALSE;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created_at;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updated_at;

    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'pending'")
    private String kyc_status = "pending";

 @PrePersist
 protected void onCreate() {
  this.created_at = LocalDateTime.now();
  this.updated_at = LocalDateTime.now();
 }

 @PreUpdate
 protected void onUpdate() {
  this.updated_at = LocalDateTime.now();
 }

 public Boolean isTwoFactorEnabled() {
  return this.two_factor_enabled;
 }



}
