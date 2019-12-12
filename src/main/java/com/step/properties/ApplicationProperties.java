package com.step.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by zhushubin  on 2019-10-29.
 * email:604580436@qq.com
 * 应用程序配置属性
 */
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    /**
     * 版本
     */
    private String version;
    /**
     * 上传文件路径
     */
    private  String profile;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
