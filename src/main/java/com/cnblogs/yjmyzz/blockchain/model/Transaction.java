package com.cnblogs.yjmyzz.blockchain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * @author yangjunming
 */
@Data
@ApiModel(description = "交易信息")
public class Transaction {

    @ApiModelProperty(value = "交易发起方", dataType = "String", example = "张三")
    private String sender;

    @ApiModelProperty(value = "交易接收方", dataType = "String", example = "李四")
    private String recepient;

    @ApiModelProperty(value = "交易额", dataType = "BigDecimal", example = "1.01")
    private BigDecimal amount;
}
