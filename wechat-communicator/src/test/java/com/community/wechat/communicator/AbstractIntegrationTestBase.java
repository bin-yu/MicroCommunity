package com.community.wechat.communicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import com.community.dataaccess.service.SpringConfig;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Import(SpringConfig.class)
public abstract class AbstractIntegrationTestBase extends AbstractTransactionalTestNGSpringContextTests{
    @Autowired
    protected TestRestTemplate restTemplate;

}
