package com.community.wechat.communicator.security;

import com.community.dataaccess.service.repo.GongZhongHao;

public class WeChatAppInfoExtractor implements IAppInfoExtractor{
    private GongZhongHao appInfo;

    public WeChatAppInfoExtractor(GongZhongHao appInfo) {
        super();
        this.appInfo = appInfo;
    }

    public String getAppId() {
        return appInfo==null?null:appInfo.getAppId();
    }
    public String getAppSecret(){
        return appInfo==null?null:appInfo.getAppSecret();
    }

    @Override
    public GongZhongHao getAppInfo() {
        return appInfo;
    }
}
