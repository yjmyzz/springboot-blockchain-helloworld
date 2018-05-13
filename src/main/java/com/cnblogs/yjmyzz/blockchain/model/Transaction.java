package com.cnblogs.yjmyzz.blockchain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {

    private String sender;

    private String recepient;

    private BigDecimal amount;
}
