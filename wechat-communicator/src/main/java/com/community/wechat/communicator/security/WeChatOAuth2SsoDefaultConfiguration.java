package com.community.wechat.communicator.security;

import java.util.Collections;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoCustomConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoDefaultConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerTokenServicesConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
@EnableConfigurationProperties(OAuth2SsoProperties.class)
@Import({ OAuth2SsoCustomConfiguration.class,
        ResourceServerTokenServicesConfiguration.class })
public class WeChatOAuth2SsoDefaultConfiguration extends OAuth2SsoDefaultConfiguration {

    public WeChatOAuth2SsoDefaultConfiguration(ApplicationContext applicationContext, OAuth2SsoProperties sso) {
        super(applicationContext, sso);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExceptionHandlingConfigurer<HttpSecurity> exceptions = http.exceptionHandling();
        ContentNegotiationStrategy contentNegotiationStrategy = http
                .getSharedObject(ContentNegotiationStrategy.class);
        if (contentNegotiationStrategy == null) {
            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        }
        MediaTypeRequestMatcher preferredMatcher = new MediaTypeRequestMatcher(
                contentNegotiationStrategy, MediaType.APPLICATION_XHTML_XML,
                new MediaType("image", "*"), MediaType.TEXT_HTML, MediaType.TEXT_PLAIN);
        preferredMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        OAuth2SsoProperties sso = this.getApplicationContext()
                .getBean(OAuth2SsoProperties.class);
        exceptions.defaultAuthenticationEntryPointFor(
                new WeChatLoginUrlAuthenticationEntryPoint(sso.getLoginPath()),
                preferredMatcher);
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/checkAccess").permitAll();
        super.configure(http);
    }

}
