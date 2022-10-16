package com.example.wallet.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    @Query("select u from UserEntity u where u.userName = ?1")
    @Nullable
    UserEntity findByUserName(String userName);
}
