package com.cnblogs.yjmyzz.blockchain.controller;

import com.cnblogs.yjmyzz.blockchain.model.BlockChain;
import com.cnblogs.yjmyzz.blockchain.model.Transaction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Scope("prototype")
@Controller
@RequestMapping(value = "/")
@Api(consumes = "application/json",
        produces = "application/json",
        protocols = "http",
        basePath = "/", value = "区块链示例")
public class BlockChainController {

    private static BlockChain blockChain;
    private static String nodeId;

    static {
        blockChain = new BlockChain();
        blockChain.newSeedBlock();
        nodeId = UUID.randomUUID().toString().replace("-", "");
    }

    @GetMapping(value = "/chain")
    @ResponseBody
    @ApiOperation(value = "查看完整的区块链")
    public Map<String, Object> fullChain() {
        Map<String, Object> map = new HashMap<>();
        map.put("chain", blockChain.getChain());
        map.put("length", blockChain.getChain().size());
        return map;
    }


    @PostMapping(value = "/transactions/new")
    @ResponseBody
    @ApiOperation(value = "创建新交易")
    public Map<String, Object> newTransaction(@RequestBody Transaction transaction) {
        long index = blockChain.newTransaction(transaction.getSender(),
                transaction.getRecepient(),
                transaction.getAmount());
        Map<String, Object> map = new HashMap<>();
        map.put("message", String.format("Transaction will be added to Block %d", index));
        return map;
    }

    @GetMapping(value = "/mine")
    @ResponseBody
    @ApiOperation(value = "挖矿")
    public Map<String, Object> mine() {
        Map<String, Object> map = new HashMap<>();
        BlockChain lastBlock = blockChain.getLastBlock();
        Integer lastProof = lastBlock.getProof();
        Integer proof = blockChain.proofOfWork(lastProof);
        blockChain.newTransaction("0", nodeId, BigDecimal.ONE);
        BlockChain block = blockChain.newBlock(proof, null);
        map.put("message", "New Block Forged");
        map.put("index", block.getIndex());
        map.put("transactions", block.getTransactions());
        map.put("proof", block.getProof());
        map.put("previousHash", block.getPreviousHash());
        return map;
    }
}
