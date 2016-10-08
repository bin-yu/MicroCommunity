package com.reborn.weixin.communicator.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import com.reborn.weixin.communicator.gongzhonghao.GongZhongHao;
import com.reborn.weixin.communicator.gongzhonghao.GongZhongHaoRepository;

@Configuration
public class WeChatResourceDetailsConfiguration {

    @Bean(name = "weChatResourceDetails")
    public OAuth2ProtectedResourceDetails resourceDetails(IAppInfoExtractor appInfoExtractor,
            @Value("${security.oauth2.client.accessTokenUri}") String accessTokenUri,
            @Value("${security.oauth2.client.userAuthorizationUri}") String userAuthorizationUri,
            @Value("${security.oauth2.client.scope[0]}") String scope,
            @Value("${security.oauth2.client.clientAuthenticationScheme}") AuthenticationScheme clientAuthenticationScheme,
            @Value("${security.oauth2.client.authentication-scheme}") AuthenticationScheme authenticationScheme) {
        WeChatAuthorizationCodeResourceDetails details = new WeChatAuthorizationCodeResourceDetails(appInfoExtractor);
        details.setAccessTokenUri(accessTokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setScope(Arrays.asList(scope));
        details.setClientAuthenticationScheme(clientAuthenticationScheme);
        details.setAuthenticationScheme(authenticationScheme);
        return details;
    }

   /* @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    protected AppInfo appInfo(@Value("#{request.getParameter('appId')}") String appId) {
        AppInfo info = new AppInfo();
        info.setAppId(appId);
        return info;
    }*/

    @Configuration
    protected static class AppInfoConfiguration {
        /*@Resource
        @Qualifier("appInfo")
        private AppInfo appInfo;*/
        @Autowired
        private GongZhongHaoRepository repo;

        @Bean
        @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
        public IAppInfoExtractor appInfoExtractor(@Value("#{request.getParameter('appId')}") String appId) {
            GongZhongHao app = null;
            if(appId!=null){
                app=repo.findOne(appId);
            }
            return new WeChatAppInfoExtractor(app);
        }
    }
}
