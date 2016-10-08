package com.reborn.weixin.communicator.security;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.RequestEnhancer;
import org.springframework.util.MultiValueMap;

public class WeChatTokenRequestEnhancer implements RequestEnhancer {

    @Override
    public void enhance(AccessTokenRequest request, OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form,
            HttpHeaders headers) {
        List<String> clientId=form.remove("client_id");
        if(clientId!=null){
            form.put("appid", clientId);
        }
        List<String> clientSecret=form.remove("client_secret");
        if(clientSecret!=null){
            form.put("secret",clientSecret);
        }
        form.remove("redirect_uri");
    }

}
