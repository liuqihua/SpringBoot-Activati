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
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestActiviti {

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 查看流程定义内容
     * Activiti7可以自动部署流程
     */
    @Test
    public void findProcess(){
        securityUtil.logInAs("jack");
//        流程定义的分页对象
        Page<ProcessDefinition> definitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        log.info("可用的流程定义总数：{}",definitionPage.getTotalItems());
        for (ProcessDefinition processDefinition : definitionPage.getContent()) {
            System.out.println("==============================");
            log.info("流程定义内容：{}",processDefinition);
            System.out.println("==============================");
        }
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcess(){
//        设置登录用户
        securityUtil.logInAs("system");
        ProcessInstance processInstance = processRuntime.
                start(ProcessPayloadBuilder
                        .start().
                        withProcessDefinitionKey("mydemo").
                        build());
        log.info("流程实例的内容，{}",processInstance);
    }

    /**
     * 执行任务
     */
    @Test
    public void doTask(){
//        设置登录用户
        securityUtil.logInAs("rose");
//        查询任务
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10));
        if(taskPage != null && taskPage.getContent()!=null){
            taskPage.getContent().stream().forEach(task -> {
                System.out.println("拾取前"+task);
                //        拾取任务
//                taskRuntime.release(new ReleaseTaskPayloadBuilder().build());
//                taskRuntime.claim(TaskPayloadBuilder.
//                        claim().
//                        withTaskId(task.getId()).build());
//                log.info("拾取后",task);
//                taskRuntime.claim(TaskPayloadBuilder.
//                        claim().
//                        withTaskId(task.getId()).
//                        build());
//                log.info("任务内容,{}",task);
                //        完成任务
//                taskRuntime.complete(TaskPayloadBuilder.
//                        complete().
//                        withTaskId(task.getId()).
//                        build());
            });
        }
//        List<String> collect = null;
//        if(taskPage != null && taskPage.getContent() != null){
//            collect = taskPage.getContent().stream().map(task -> {
//                return task.getName();
//            }).collect(Collectors.toList());
//        }
//        if(collect!=null){
//            collect.stream().forEach(str -> System.out.println(str));
//        }
//        Optional.ofNullable(taskPage.getContent()).ifPresent(tasks -> {
//
//           List<String> collect= tasks.stream().map(task -> {
//                return task.getName();
//            }).collect(Collectors.toList());
//        });



//        taskPage.getContent().stream().forEach(task -> System.out.println(task));
//        Optional.ofNullable(taskPage.getContent()){
//
//        Optional.ofNullable(taskPage.getContent()).map(tasks -> {
//            return "hehe";
//            });
//        });




//        if(taskPage != null && taskPage.getTotalItems()>0){
//            for (Task task : taskPage.getContent()) {
//                //        拾取任务
//                taskRuntime.claim(TaskPayloadBuilder.
//                        claim().
//                        withTaskId(task.getId()).
//                        build());
//                log.info("任务内容,{}",task);
//                //        完成任务
//                taskRuntime.complete(TaskPayloadBuilder.
//                        complete().
//                        withTaskId(task.getId()).
//                        build());
//            }
//        }


    }

    @Autowired
    ProcessEngine processEngine;
    @Autowired
    RuntimeService runtimeService;
    public void select(){

//        List<org.activiti.engine.runtime.ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
//        list.stream().forEach(lis -> System.out.println(lis));

    }

}
