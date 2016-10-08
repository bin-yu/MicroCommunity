package com.community.wechat.communicator.security;

import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

public class WeChatAuthorizationCodeResourceDetails extends AuthorizationCodeResourceDetails{
  
    private IAppInfoExtractor appInfo;

    public WeChatAuthorizationCodeResourceDetails(IAppInfoExtractor appInfo) {
        this.appInfo=appInfo;
    }

    @Override
    public String getClientId() {
        return appInfo.getAppId();
    }

    @Override
    public String getClientSecret() {
        return appInfo.getAppSecret();
    }
    @Override
    public boolean isAuthenticationRequired() {
        return getClientAuthenticationScheme() != AuthenticationScheme.none;
    }
}
