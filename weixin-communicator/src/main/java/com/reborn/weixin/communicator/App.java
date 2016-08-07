package com.reborn.weixin.communicator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@RestController
@EnableAutoConfiguration
public class App {
    Logger logger=LoggerFactory.getLogger(App.class);
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }
    
    @RequestMapping("/checkAccess")
    public String checkAccess(@RequestParam("signature") String signature,@RequestParam("timestamp")String timestamp,@RequestParam("nonce")String nonce,@RequestParam("echostr")String echostr){
        logger.info("signature="+signature+",timestamp="+timestamp+",nonce="+nonce+",echostr="+echostr);
        return echostr;
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

}
