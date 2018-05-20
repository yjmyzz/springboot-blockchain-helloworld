package com.cnblogs.yjmyzz.blockchain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jimmy
 */
@Data
@ApiModel(description = "注册节点-请求参数")
public class RegisterRequest {

    @ApiModelProperty(value = "集群中的节点列表", dataType = "List<String>")
    private List<String> nodes;
}
