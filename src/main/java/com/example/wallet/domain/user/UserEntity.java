package com.example.wallet.domain.user;

import com.example.wallet.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name = "app_user")
@Getter @Setter @Accessors(fluent = true)
public class UserEntity extends BaseEntity
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    private Boolean isFailedLockActivated;
    private Integer failedLockCount;
    private Integer failedLockLimit = 4;
    private Boolean failedLocked;
    private LocalDateTime unlockTime;
    private LocalDateTime lastLoginSucceed;

    String createdUser;
    LocalDateTime createdTime;
    String lastUpdatedUser;
    LocalDateTime lastUpdatedTime;
    Boolean isDeleted = false;
    Boolean isActivated = true;

    String verificationCode;
    Boolean isVerified = false;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> userRoles = new LinkedHashSet<>();
}
