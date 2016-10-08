package com.reborn.weixin.communicator.gongzhonghao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.reborn.weixin.communicator.AbstractTestBase;

public class GongZhongHaoRepositoryTest extends AbstractTestBase{
    @Autowired
    private GongZhongHaoRepository repo;
    @Test
    public void testFindOne(){
        GongZhongHao one=repo.findOne("wx73f1e72a530acc34");
        Assert.assertNotNull(one);
    }
}
