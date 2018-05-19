package com.cnblogs.yjmyzz.blockchain.utils;

import com.cnblogs.yjmyzz.blockchain.model.BlockChain;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author yangjunming
 */
@Component
public class BeanInjector {

    private BlockChain blockChain;
    private String nodeId;

    @Bean("nodeId")
    private String nodeId() {
        synchronized (this) {
            if (StringUtils.isEmpty(nodeId)) {
                nodeId = UUID.randomUUID().toString().replace("-", "");
            }
        }
        return nodeId;
    }

    @Bean("blockChain")
    private BlockChain blockChain() {
        synchronized (this) {
            if (blockChain == null) {
                blockChain = new BlockChain();
                blockChain.newSeedBlock();
            }
        }
        return blockChain;
    }


}
