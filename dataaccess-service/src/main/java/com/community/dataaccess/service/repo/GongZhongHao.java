package com.community.dataaccess.service.repo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GongZhongHao {
    private int xiaoQuId;
    @Id
    private String appId;
    private String appSecret;
    private String name;
    
    
    public GongZhongHao(int xiaoQuId, String appId, String appSecret, String name) {
        super();
        this.xiaoQuId = xiaoQuId;
        this.appId = appId;
        this.appSecret = appSecret;
        this.name = name;
    }


    public GongZhongHao() {
        super();
    }
    
    
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
    public int getXiaoQuId() {
        return xiaoQuId;
    }
    public void setXiaoQuId(int xiaoQuId) {
        this.xiaoQuId = xiaoQuId;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appId == null) ? 0 : appId.hashCode());
        result = prime * result + ((appSecret == null) ? 0 : appSecret.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + xiaoQuId;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GongZhongHao other = (GongZhongHao) obj;
        if (appId == null) {
            if (other.appId != null)
                return false;
        } else if (!appId.equals(other.appId))
            return false;
        if (appSecret == null) {
            if (other.appSecret != null)
                return false;
        } else if (!appSecret.equals(other.appSecret))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (xiaoQuId != other.xiaoQuId)
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "GongZhongHao [xiaoQuId=" + xiaoQuId + ", appId=" + appId + ", appSecret=" + appSecret + ", name=" + name + "]";
    }
    
   
    
}
