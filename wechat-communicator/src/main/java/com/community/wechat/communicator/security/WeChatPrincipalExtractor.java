package com.community.wechat.communicator.security;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

public class WeChatPrincipalExtractor implements PrincipalExtractor{

    public WeChatPrincipalExtractor() {
    }

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
        WeChatUserInfo user=new WeChatUserInfo();
        user.setOpenid((String) map.get("openid"));
        user.setUnionid((String)map.get("unionid"));
        user.setNickname((String) map.get("nickname"));
        user.setCity((String) map.get("city"));
        user.setCountry((String) map.get("country"));
        user.setProvince((String) map.get("province"));
        user.setSex((Integer) map.get("sex"));
        return user;
    }

}
