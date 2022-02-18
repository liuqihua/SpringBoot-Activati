package com.itheima.controller;

import com.itheima.entity.Evection;
import com.itheima.entity.FlowInfo;
import com.itheima.service.ActFlowCommService;
import com.itheima.service.EvectionService;
import com.itheima.service.FlowService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程管理
 */
@RestController
public class FlowController {


    @Autowired
    private FlowService flowService;
    @Autowired
    private ActFlowCommService actFlowCommService;


    /**
     * 查询所有流程
     * @return
     */
    @GetMapping("/flow/findAll")
    public List<FlowInfo> findAllFlow(){
        return flowService.findAllFlow();
    }
    /**
     * 部署流程
     * @param request
     * @return 0-部署失败  1- 部署成功  2- 已经部署过
     */
    @PutMapping("/flow/deployment/{id}")
    public Integer deployment(HttpServletRequest request, @PathVariable(name = "id")Long id){
        FlowInfo flowInfo = flowService.findOneFlow(id);
        if(flowInfo == null && flowInfo.getState() == 0){
            return 2;
        }
        actFlowCommService.saveNewDeploy(flowInfo);
        return flowService.updateDeployState(id);
    }



    /**
     * 查询用户任务
     * @param request
     * @return
     */
    @GetMapping("/flow/findUserTask")
    public List<Map<String,Object>> findUserTask(HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("userid");
        return flowService.findUserTask(userId);
    }

    /**
     * 查询任务详细信息
     * @param request
     * @return
     */
    @GetMapping("/flow/findTaskInfo")
    public List<Map<String,Object>> findTaskInfo(HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("userid");
        return flowService.findTaskInfo(userId);
    }

    /**
     * 完成任务
     * @param request
     */
    @PutMapping("/flow/completeTask/{taskId}")
    public void completeTask(HttpServletRequest request,@PathVariable("taskId")String taskId){
        Long userId = (Long)request.getSession().getAttribute("userid");
        flowService.completeTask(taskId,userId);
    }

    /**
     * 查询
     * @return
     */
    @GetMapping("/flow/findFlowTask/{id}")
    public Map<String,Object> findFlowTask(@PathVariable(name = "id")Long id){
        String businessKey = "evection:"+id;
        actFlowCommService.searchHistory(businessKey);
        return null;
    }
    @GetMapping("/flow/findFlowPower/{}")
    public Map<String,String> findFlowPower(){
        return new HashMap<>();
    }
}
