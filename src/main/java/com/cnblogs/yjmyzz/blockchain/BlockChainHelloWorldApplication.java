package com.cnblogs.yjmyzz.blockchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangjunming
 */
@SpringBootApplication
@Controller
public class BlockChainHelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockChainHelloWorldApplication.class, args);
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/swagger-ui.html";
    }

}
