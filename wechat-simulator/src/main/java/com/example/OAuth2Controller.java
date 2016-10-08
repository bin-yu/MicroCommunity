package com.example;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class OAuth2Controller {
    @GetMapping("/connect/oauth2/authorize")
    public ModelAndView  grantCode(@RequestParam("redirect_uri") String redirectUri,@RequestParam("state")String state, HttpServletRequest request){
        printRequest(request);
        Map<String, String> model=new HashMap<String,String>();
        model.put("code", "grant_code");
        model.put("state", state);
        return new ModelAndView("redirect:"+redirectUri,model);
        
    }
    @GetMapping("/sns/oauth2/access_token")
    public ResponseEntity<WeChatAccessToken>  retrieveAccessToken(HttpServletRequest request){
        printRequest(request);
        WeChatAccessToken token = new WeChatAccessToken("accessToken", System.currentTimeMillis()+99999L, "refreshToken", "openid", "snsapi_userinfo");
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        ResponseEntity<WeChatAccessToken> response=new ResponseEntity<WeChatAccessToken>(token,headers,HttpStatus.OK);
        return response;
    }
    
    @GetMapping("/sns/userinfo")
    public ResponseEntity<WeChatUserInfo>  getUserInfo(HttpServletRequest request){
        printRequest(request);
        WeChatUserInfo userInfo = new WeChatUserInfo("openid", "nickname", 1, "province", "city", "country", "headimgurl", "unionid",
                new String[]{});
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        ResponseEntity<WeChatUserInfo> response=new ResponseEntity<WeChatUserInfo>(userInfo,headers,HttpStatus.OK);
        return response;
        
    }
    private void printRequest(HttpServletRequest req) {
        StringBuilder sb=new StringBuilder("New Request : \n");
        sb.append(req.getMethod()+" "+req.getRequestURI());
        if(req.getQueryString()!=null){
            sb.append("?"+req.getQueryString());
        }
        Enumeration<String> headers = req.getHeaderNames();
        sb.append("\nHeaders:\n");
        while(headers.hasMoreElements()){
            String key=headers.nextElement();
            String value=req.getHeader(key);
            sb.append("\t"+key+" : "+value+"\n");
        }
       
        
        System.out.println(sb.toString());
    }

}
