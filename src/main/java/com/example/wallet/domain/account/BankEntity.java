package com.example.wallet.domain.account;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Table(name = "bank")
@Entity @Getter @Setter @Accessors(fluent = true)
public class BankEntity
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id", nullable = false)
    private Long id;
    private String fullName;
    private String shortName;
    private String bankCode;
}
