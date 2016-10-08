package com.reborn.weixin.communicator.security;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;

@Configuration
@EnableOAuth2Client
public class WeChatOAuth2ClientConfiguration extends OAuth2ClientConfiguration {

    @Override
    @Bean
    public OAuth2ClientContextFilter oauth2ClientContextFilter() {
        return new WeChatOAuth2ClientContextFilter();
    }
    @Bean
    public PrincipalExtractor principalExtractor(){
        return new WeChatPrincipalExtractor();
    }
    @Bean
    public UserInfoRestTemplateFactory userInfoRestTemplateFactory(
            ObjectProvider<List<UserInfoRestTemplateCustomizer>> customizersProvider,
            ObjectProvider<OAuth2ProtectedResourceDetails> detailsProvider,
            ObjectProvider<OAuth2ClientContext> oauth2ClientContextProvider,final OAuth2RestTemplate oauth2RestTemplate) {
        return new UserInfoRestTemplateFactory(customizersProvider, detailsProvider,
                oauth2ClientContextProvider){
            public OAuth2RestTemplate getUserInfoRestTemplate() {
                return oauth2RestTemplate;
            }
        };
    }
   
    
    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, @Qualifier("weChatResourceDetails") OAuth2ProtectedResourceDetails weChatResourceDetails) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(acceptsUntrustedCertsHttpClient());
        OAuth2RestTemplate restTemplate = new WeChatOAuth2RestTemplate(weChatResourceDetails, oauth2ClientContext);
        AuthorizationCodeAccessTokenProvider accessTokenProvider = new WeChatAuthorizationCodeAccessTokenProvider();
        accessTokenProvider.setRequestFactory(clientHttpRequestFactory);
        accessTokenProvider.setTokenRequestEnhancer(new WeChatTokenRequestEnhancer());
        restTemplate.setAccessTokenProvider(accessTokenProvider);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        
        MappingJackson2HttpMessageConverter jsonConverter=new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        ArrayList<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>(1);
        list.add(jsonConverter);
        restTemplate.setMessageConverters(list);
        return restTemplate;
    }

    
    public static CloseableHttpClient acceptsUntrustedCertsHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder b = HttpClientBuilder.create();

        // setup a Trust Strategy that allows all certificates.
        //
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                return true;
            }
        }).build();
        b.setSSLContext(sslContext);

        // don't check Hostnames, either.
        //      -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

        // here's the special part:
        //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
        //      -- and create a Registry, to register it.
        //
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();

        // now, we create connection-manager using our Registry.
        //      -- allows multi-threaded use
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
        connMgr.setMaxTotal(200);
        connMgr.setDefaultMaxPerRoute(100);
        b.setConnectionManager( connMgr);

        // finally, build the HttpClient;
        //      -- done!
        CloseableHttpClient client = b.build();

        return client;
    }
}
