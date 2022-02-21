package com.test;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test2 {
    @Autowired
    RuntimeService runtimeService;

    @Test
    public void select(){
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processDefinitionKey("myPrice").list();
        list.stream().forEach(s -> {

            System.out.println("================================");
            System.out.println("流程实例id"+s.getProcessInstanceId());
            System.out.println("所属流程定义id"+s.getProcessDefinitionId());
            System.out.println("是否执行完成"+s.isEnded());
            System.out.println("是否暂停"+s.isSuspended());
            System.out.println("当前活动标识"+s.getActivityId());
            System.out.println("================================");

        });



    }
}
