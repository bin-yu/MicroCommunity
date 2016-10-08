package com.reborn.weixin.communicator.security;

import com.reborn.weixin.communicator.gongzhonghao.GongZhongHao;

public interface IAppInfoExtractor {
    public String getAppId();
    public String getAppSecret();
    GongZhongHao getAppInfo();
}
