package com.test;

import com.itheima.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestActiviti2 {

    @Autowired
    SecurityUtil securityUtil;
    @Autowired
    ProcessRuntime processRuntime;
    @Autowired
    TaskRuntime taskRuntime;
    /**
     * 查看流程定义内容
     * Activiti7可以自动部署流程
     */
    @Test
    public void findProcess() {
        securityUtil.logInAs("jack");
//        流程定义的分页对象
        Page<ProcessDefinition> definitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        log.info("可用的流程定义总数：{}", definitionPage.getTotalItems());
        for (ProcessDefinition processDefinition : definitionPage.getContent()) {
            System.out.println("==============================");
            log.info("流程定义内容：{}", processDefinition);
            System.out.println("==============================");
        }
    }


    /**
     * 启动流程
     */
    @Test
    public void startProcess() {
//        设置登录用户
        securityUtil.logInAs("system");
        HashMap<String, Object> map = new HashMap<>();
        map.put("assignee", "system");
        map.put("node", "2");
        ProcessInstance processInstance = processRuntime.
                start(ProcessPayloadBuilder
                        .start().withVariables(map)
                                .
                        withProcessDefinitionKey("myPrice").
                        build());
        log.info("流程实例的内容，{}", processInstance);
    }


    /**
     * 执行任务
     */
    @Test
    public void doTask() {
//        设置登录用户
        securityUtil.logInAs("system");
//        查询任务

        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
        List<Task> content = tasks.getContent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("assignee","zhang");
        map.put("approve",0);

        content.stream().forEach(task -> System.out.println(task));
        taskRuntime.complete(TaskPayloadBuilder.
                        complete().
                        withVariables(map)
                        .withTaskId("b7df21d8-9316-11ec-8f83-005056c00008").
                        build());
        }

        @Autowired
        ProcessEngine processEngine;

        @Test
        public void seleectP (){
            RuntimeService runtimeService = processEngine.getRuntimeService();
//            List<org.activiti.engine.runtime.ProcessInstance> myPrice = runtimeService.createProcessInstanceQuery().
//                    processDefinitionKey("myPrice").
//                    list();



//            myPrice.stream().forEach(processInstance -> System.out.println(processInstance));
        }
        @Autowired
        RepositoryService repositoryService;
        @Test
        public void delete(){
            repositoryService.deleteDeployment("",true);


        }


        @Autowired
        HistoryService historyService;
        @Test
        public void msg(){
            HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
//            HistoricActivityInstanceQuery historicActivityInstanceQuery1 = historicActivityInstanceQuery.processInstanceId("2051");
            List<HistoricActivityInstance> list = historicActivityInstanceQuery
//                    .processDefinitionId("b7d9f1af-9316-11ec-8f83-005056c00008")
                    .processInstanceId("b7d9f1af-9316-11ec-8f83-005056c00008")
                    .list();
            if(list != null) {
                list.stream().forEach(h -> {
                    System.out.println("===================================");
                    System.out.println(h.getActivityId());
                    System.out.println(h.getActivityName());
                    System.out.println(h.getProcessDefinitionId());
                    System.out.println(h.getProcessInstanceId());

                    System.out.println("===================================");
                });
            }else {
                System.out.println(list);
            }
        }
}

