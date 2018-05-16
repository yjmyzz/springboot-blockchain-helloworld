package com.cnblogs.yjmyzz.blockchain.model;

import com.alibaba.fastjson.JSON;
import com.cnblogs.yjmyzz.blockchain.utils.SHAUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "区块链")
public class BlockChain {

    @ApiModelProperty(value = "当前交易列表", dataType = "List<Transaction>")
    private List<Transaction> currentTransactions;

    @ApiModelProperty(value = "所有交易列表", dataType = "List<Transaction>")
    private List<Transaction> transactions;

    @ApiModelProperty(value = "区块列表", dataType = "List<BlockChain>")
    private List<BlockChain> chain;

    @ApiModelProperty(value = "上一个区块的哈希值", dataType = "String")
    private String previousHash = "";

    @ApiModelProperty(value = "工作量证明", dataType = "Integer", example = "100")
    private Integer proof = 0;

    @ApiModelProperty(value = "当前区块的索引序号", dataType = "Long", example = "2")
    private Long index = 0L;

    @ApiModelProperty(value = "当前区块的时间戳", dataType = "Long", example = "1526458171000")
    private Long timestamp = 0L;

    public BlockChain() {
        currentTransactions = new ArrayList<>();
        chain = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public static String getHash(BlockChain block) {
        String json = JSON.toJSONString(block);
        return SHAUtils.getSHA256Str(json);
    }

    public BlockChain getLastBlock() {
        if (CollectionUtils.isEmpty(chain)) {
            return null;
        }
        return chain.get(chain.size() - 1);
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
