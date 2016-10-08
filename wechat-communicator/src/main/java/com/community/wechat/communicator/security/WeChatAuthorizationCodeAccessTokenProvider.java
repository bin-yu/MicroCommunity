package com.community.wechat.communicator.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

public class WeChatAuthorizationCodeAccessTokenProvider extends AuthorizationCodeAccessTokenProvider {

    private List<HttpMessageConverter<?>> messageConverters=new ArrayList<HttpMessageConverter<?>>(1);

    public WeChatAuthorizationCodeAccessTokenProvider() {
        MappingJackson2HttpMessageConverter jsonConverter=new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        messageConverters.add(jsonConverter);
    }

    @Override
    protected HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    protected RequestCallback getRequestCallback(OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form, HttpHeaders headers) {
        return new WeChatOAuth2AuthTokenCallback(headers);
    }
    @Override
    protected ResponseExtractor<OAuth2AccessToken> getResponseExtractor() {
        return new HttpMessageConverterExtractor<OAuth2AccessToken>(OAuth2AccessToken.class, messageConverters);
    }
    @Override
    public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request)
            throws UserRedirectRequiredException, UserApprovalRequiredException, AccessDeniedException, OAuth2AccessDeniedException {
        if(details.getClientId() == null){
            throw new InvalidClientException("appId is missing or invalid!");
        }
        return super.obtainAccessToken(details, request);
    }
    
    private static class WeChatOAuth2AuthTokenCallback implements RequestCallback {


        private final HttpHeaders headers;

        private WeChatOAuth2AuthTokenCallback(HttpHeaders headers) {
            this.headers = headers;
        }

        public void doWithRequest(ClientHttpRequest request) throws IOException {
            request.getHeaders().putAll(this.headers);
            request.getHeaders().setAccept(
                    Arrays.asList(MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON_UTF8));
            
        }
    }
    
}
