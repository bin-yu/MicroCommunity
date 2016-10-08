package com.community.wechat.communicator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.community.dataaccess.service.SpringConfig;

@SpringBootApplication
@Import(SpringConfig.class)
public class RootConfiguration {

}
