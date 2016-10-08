package com.community.wechat.communicator.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.util.UriComponentsBuilder;

public class WeChatOAuth2ClientContextFilter extends OAuth2ClientContextFilter {
    private static final String WECHAT_REDIRECT = "wechat_redirect";
    private static final String SECRET = "secret";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String CLIENT_ID = "client_id";
    private static final String APPID = "appid";
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    protected void redirectUser(UserRedirectRequiredException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        adjustReqParam(e.getRequestParams());
        String redirectUri = e.getRedirectUri();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(redirectUri);
        Map<String, String> requestParams = e.getRequestParams();
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            builder.queryParam(param.getKey(), param.getValue());
        }

        if (e.getStateKey() != null) {
            builder.queryParam("state", e.getStateKey());
        }
        builder.fragment(WECHAT_REDIRECT);
        this.redirectStrategy.sendRedirect(request, response, builder.build()
                .encode().toUriString());
    }

    private static Map<String, String> adjustReqParam(Map<String, String> requestParams) {
        String clientId=requestParams.remove(CLIENT_ID);
        if(clientId!=null){
            requestParams.put(APPID, clientId);
        }
        String clientSecret=requestParams.remove(CLIENT_SECRET);
        if(clientSecret!=null){
            requestParams.put(SECRET,clientSecret);
        }
        return requestParams;
    }

}
