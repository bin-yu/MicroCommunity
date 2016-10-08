package com.community.dataaccess.service.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.community.dataaccess.service.AbstractTestBase;

public class GongZhongHaoRepositoryTest extends AbstractTestBase{
    Logger logger=LoggerFactory.getLogger(GongZhongHaoRepositoryTest.class);
    @Autowired
    private GongZhongHaoRepository repo;
    @Test
    public void testFindOne(){
        GongZhongHao one=repo.findOne("wx73f1e72a530acc34");
        Assert.assertNotNull(one);
        logger.info("FindOne Return:"+one.toString());
        Assert.assertEquals(one, new GongZhongHao(1,"wx73f1e72a530acc34","d8d6ca01bde0c496e019d9fa837db4e0","Bin-Test"));
    }
}
