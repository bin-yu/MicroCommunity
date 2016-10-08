package com.community.wechat.communicator.security;

import com.community.dataaccess.service.repo.GongZhongHao;

public interface IAppInfoExtractor {
    public String getAppId();
    public String getAppSecret();
    GongZhongHao getAppInfo();
}
