package com.community.wechat.communicator.security;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class WeChatOAuth2RestTemplate extends OAuth2RestTemplate {

    private static final String OPENID = "openid";

    public WeChatOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource) {
        super(resource);
    }

    public WeChatOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        super(resource, context);
    }

    @Override
    protected URI appendQueryParameter(URI uri, OAuth2AccessToken accessToken) {
        try {

            // TODO: there is some duplication with UriUtils here. Probably unavoidable as long as this
            // method signature uses URI not String.
            String query = uri.getRawQuery(); // Don't decode anything here
            String queryFragment = constructTokenParam(accessToken);
            if (query == null) {
                query = queryFragment;
            }
            else {
                query = query + "&" + queryFragment;
            }

            // first form the URI without query and fragment parts, so that it doesn't re-encode some query string chars
            // (SECOAUTH-90)
            URI update = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), null,
                    null);
            // now add the encoded query string and the then fragment
            StringBuffer sb = new StringBuffer(update.toString());
            sb.append("?");
            sb.append(query);
            if (uri.getFragment() != null) {
                sb.append("#");
                sb.append(uri.getFragment());
            }

            return new URI(sb.toString());

        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not parse URI", e);
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Could not encode URI", e);
        }
    }

    private String constructTokenParam(OAuth2AccessToken accessToken) throws UnsupportedEncodingException {
        String queryParam = getResource().getTokenName() + "=" + URLEncoder.encode(accessToken.getValue(), "UTF-8");
        
        String openid = (String) accessToken.getAdditionalInformation().get(OPENID);
        if(openid!=null){
            queryParam += "&" + OPENID+"="+openid;
        }
        return queryParam;
    }

}
