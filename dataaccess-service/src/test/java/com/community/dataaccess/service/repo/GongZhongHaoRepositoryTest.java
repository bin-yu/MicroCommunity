package com.community.dataaccess.service.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.community.dataaccess.service.AbstractTestBase;

public class GongZhongHaoRepositoryTest extends AbstractTestBase{
    @Autowired
    private GongZhongHaoRepository repo;
    @Test
    public void testFindOne(){
        GongZhongHao one=repo.findOne("wx73f1e72a530acc34");
        Assert.assertNotNull(one);
    }
}
