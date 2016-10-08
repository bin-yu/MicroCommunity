package com.community.dataaccess.service.repo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GongZhongHao {
    @Id
    private String appId;
    private String appSecret;
    private String name;
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getAppSecret() {
        return appSecret;
    }
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
