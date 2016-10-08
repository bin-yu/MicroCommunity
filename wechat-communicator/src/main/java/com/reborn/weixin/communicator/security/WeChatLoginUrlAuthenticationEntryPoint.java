package com.reborn.weixin.communicator.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class WeChatLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public WeChatLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        // TODO Auto-generated method stub
        String loginUrl = super.determineUrlToUseForThisRequest(request, response, exception);
        String appId=request.getParameter("appId");
        if(appId!=null){
            loginUrl+="?appId="+appId;
        }
        return loginUrl;
    }
    
}
