package com.example.wallet.domain.account;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity @Getter @Setter @Accessors(fluent = true)
@Table(name = "bank_account")
public class BankAccountEntity
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne @JoinColumn(name = "bank_id")
    BankEntity bank;
    String accountNumber;
    @ManyToOne @JoinColumn(name = "account_id")
    AppAccountEntity account;

}
