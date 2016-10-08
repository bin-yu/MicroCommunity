package com.reborn.weixin.communicator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestControllerIntegrationTest extends AbstractIntegrationTestBase{
    
    @Test
    public void testHomePage() {
        String body = restTemplate.getForObject("/", String.class);
        Assert.assertNotNull(body);
        System.out.println(body);
    }
    @Test
    public void testCheckAccess(){
        String body = restTemplate.getForObject("/checkAccess?signature=sig111&timestamp=22222&nonce=xxxx&echostr=hello", String.class);
        Assert.assertEquals(body,"hello");
        ResponseEntity<String> response = restTemplate.getForEntity("/checkAccess?signature=sig111&timestamp=22222&nonce=xxxx", String.class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Assert.assertNull(response.getBody());
    }
}
