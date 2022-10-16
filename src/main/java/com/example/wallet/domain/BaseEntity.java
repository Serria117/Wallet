package com.example.wallet.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter @Setter @Accessors(fluent = true)
public abstract class BaseEntity
{
    String createdUser;
    LocalDateTime createdTime;
    String lastUpdatedUser;
    LocalDateTime lastUpdatedTime;
    Boolean isDeleted;
    Boolean isActivated;
}
