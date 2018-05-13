package com.cnblogs.yjmyzz.blockchain;

import com.cnblogs.yjmyzz.blockchain.model.BlockChain;
import com.cnblogs.yjmyzz.blockchain.model.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@Controller
public class BlockChainHelloWorldApplication {

    private static BlockChain blockChain;
    private static String nodeId;

    public static void main(String[] args) {

        SpringApplication.run(BlockChainHelloWorldApplication.class, args);
        blockChain = new BlockChain();
        blockChain.newSeedBlock();
        nodeId = UUID.randomUUID().toString().replace("-", "");
    }

    @GetMapping(value = "/chain")
    @ResponseBody
    public Map<String, Object> fullChiain() {
        Map<String, Object> map = new HashMap<>();
        map.put("chain", blockChain.getChain());
        map.put("length", blockChain.getChain().size());
        return map;
    }


    @PostMapping(value = "/transactions/new")
    @ResponseBody
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
