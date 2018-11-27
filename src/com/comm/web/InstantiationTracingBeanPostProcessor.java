package com.comm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.comm.service.SystemEnvService;
import com.comm.web.common.util.PropUtil;

@Component
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SystemEnvService systemEnvService;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 加载系统表静态变量
        PropUtil.initSystemEnvStatic(systemEnvService);
        
        
        // 获取配置文件内容
        //PropUtil.initKey(Const.PRIVATEKEY_PROP_PATH);
        
    }
}
