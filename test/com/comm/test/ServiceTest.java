package com.comm.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.comm.model.StoryInfo;
import com.comm.service.StoryInfoService;

public class ServiceTest extends BaseTest {
    
    @Autowired
    private StoryInfoService sis;
    
    @Test
    public void m1() {
        
        StoryInfo storyInfo = new StoryInfo();
        
        storyInfo.setBookId("aaaaa");
        storyInfo.setBookDesc("啊啊啊");
        sis.save(storyInfo);
        
    }
    
    @Test
    public void m2() {
        System.out.println("100111".substring(0, 3));
    }
}
