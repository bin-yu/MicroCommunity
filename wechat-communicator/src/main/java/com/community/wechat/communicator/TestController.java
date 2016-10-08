package com.community.wechat.communicator;

import java.security.Principal;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.community.dataaccess.service.repo.GongZhongHao;
import com.community.wechat.communicator.security.IAppInfoExtractor;

@RestController
public class TestController {
    Logger logger=LoggerFactory.getLogger(App.class);
    @Autowired
    private IAppInfoExtractor appInfoExtractor;
    @RequestMapping("/")
    String home(HttpServletRequest req) {
        String reqInfo = printRequest(req);
        logger.info(reqInfo);
        return reqInfo;
    }
    
    @RequestMapping("/checkAccess")
    public String checkAccess(@RequestParam("signature") String signature,@RequestParam("timestamp")String timestamp,@RequestParam("nonce")String nonce,@RequestParam(name="echostr",required=false,defaultValue="")String echostr,HttpServletRequest req){
        String reqInfo = printRequest(req);
        logger.info(reqInfo);
        return echostr;
    }
    @RequestMapping("bind")
    public String bind(HttpServletRequest req){
        return printRequest(req);
    }
    @RequestMapping("querydebt")
    public String querydebt(HttpServletRequest req){
        return printRequest(req);
    }

    @RequestMapping("me")
    public Principal getUserInfo(Principal user){
        return user;
    }
    @RequestMapping("appInfo")
    public GongZhongHao getAppInfo(){
        return appInfoExtractor.getAppInfo();
    }
    private String printRequest(HttpServletRequest req) {
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
       
        
        return sb.toString();
    }
    
}
