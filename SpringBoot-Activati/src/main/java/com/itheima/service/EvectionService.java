package com.itheima.service;

import com.itheima.entity.Evection;
import com.itheima.mapper.EvectionMapper;
import com.itheima.utils.CommonUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.event.EventContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvectionService implements IActFlowCustomService{

    @Autowired
    private EvectionMapper evectionMapper;

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 查询出差列表
     * @param userId
     * @return
     */
    public List<Evection> findList(Long userId) {
        return evectionMapper.selectAll(userId);
    }

    /**
     * 查询一条出差信息
     * @param id
     * @return
     */
    public Evection findOne(Long id){
        return evectionMapper.selectOne(id);
    }

    /**
     * 添加 出差任务
     * @param evection
     */
    @Transactional(rollbackFor = Exception.class)
    public int addEvection(Evection evection) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        evection.setAssignee(userDetails.getUsername());
        ProcessInstance processInstance = null;
        try {
            Map<String, Object> map = CommonUtil.objectToMap(evection);
            map.put("assignee",evection.getAssignee());
            map.put("deployId",evection.getDeployId());

            processInstance = runtimeService.startProcessInstanceById(evection.getDeployId(),"businessKey", map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        processInstance.getName();

//        int code = evectionMapper.save(evection);
        return 1;
    }
    /**
     * 设置出差申请的 流程变量
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> setvariables(Long id)
    {
        Evection evection = this.findOne(id);
        //设置流程变量
        Map<String,Object> variables = new HashMap<>();
        variables.put("assignee0",1);
        variables.put("assignee1",2);
        variables.put("assignee2",3);
        variables.put("assignee3",4);
        variables.put("evection",evection);
        return variables;
    }

    @Override
    public void startRunTask(Long id) {
        evectionMapper.startTask(id);
    }

    @Override
    public void endRunTask(Long id) {
        evectionMapper.endTask(id);
    }


}
