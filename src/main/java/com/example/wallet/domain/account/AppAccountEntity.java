package com.example.wallet.domain.account;

import com.example.wallet.domain.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity @Getter @Setter @Accessors(fluent = true)
@Table(name = "app_account")
public class AppAccountEntity
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne @JoinColumn(name = "user_id")
    private UserEntity user;
    private AccountType accountType = AccountType.PERSONAL;

    private String userIdentityNumber;
    private String userNewIdentityNumber;
    private String linkedPhoneNumber;
    private String physicalAddress;

//    @OneToMany
//    private Set<BankAccountEntity> linkedBank;

    private Long transLimit; //maximum amount of money in a single transaction

    private Long dailyPaymentLimit;
    private Long dailyDepositLimit;
    private Long dailyWithdrawLimit;

    private Long monthlyPaymentLimit;
    private Long monthlyDepositLimit;
    private Long monthlyWithdrawLimit;


}
