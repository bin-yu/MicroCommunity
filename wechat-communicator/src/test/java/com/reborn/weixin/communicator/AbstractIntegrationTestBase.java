package com.reborn.weixin.communicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTestBase extends AbstractTransactionalTestNGSpringContextTests{
    @Autowired
    protected TestRestTemplate restTemplate;

}
