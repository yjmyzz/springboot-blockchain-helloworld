package com.cnblogs.yjmyzz.blockchain.model;

import com.alibaba.fastjson.JSON;
import com.cnblogs.yjmyzz.blockchain.SHAUtils;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BlockChain {

    private List<Transaction> currentTransactions;

    private List<Transaction> transactions;

    private List<BlockChain> chain;

    private String previousHash = "";

    private Integer proof = 0;

    private Long index = 0L;

    private Long timestamp = 0L;

    public BlockChain getLastBlock() {
        if (CollectionUtils.isEmpty(chain)) {
            return null;
        }
        return chain.get(chain.size() - 1);
    }

    public static String getHash(BlockChain block) {
        String json = JSON.toJSONString(block);
        return SHAUtils.getSHA256Str(json);
    }

    public BlockChain newBlock(Integer proof, String previousHash) {
        BlockChain block = new BlockChain();
        block.index = chain.size() + 1L;
        block.timestamp = System.currentTimeMillis();
        block.proof = proof;
        block.previousHash = previousHash;
        transactions.addAll(currentTransactions);
        currentTransactions.clear();
        chain.add(block);
        return block;
    }

    public Long newTransaction(String sender, String recipient, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setRecepient(recipient);
        transaction.setAmount(amount);
        currentTransactions.add(transaction);
        return getLastBlock().index + 1;
    }

    public Integer proofOfWork(Integer lastProof) {
        int proof = 0;
        while (!validProof(lastProof, proof)) {
            proof += 1;
        }
        return proof;
    }

    public Boolean validProof(Integer lastProof, Integer proof) {
        String guessHash = SHAUtils.getSHA256Str(String.format("{%d}{%d}", lastProof, proof));
        System.out.println(guessHash);
        return guessHash.startsWith("0000");
    }


    public BlockChain() {
        currentTransactions = new ArrayList<>();
        chain = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public void newSeedBlock() {
        newBlock(100, "1");
    }


//    public static void main(String[] args) {
//        BlockChain chain = new BlockChain();
//        chain.newSeedBlock();
//        System.out.println(JSON.toJSONString(chain, true));
////        System.out.println(chain.validProof(100L, 1L));
//    }

}
