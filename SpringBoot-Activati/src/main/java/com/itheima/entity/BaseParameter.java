package com.itheima.entity;

import lombok.Data;

@Data
public class BaseParameter {
    //流程部署id 流程部署时的id
    private String deployId;
    //流程审批人
    private String assignee;
    //
    private String createUser;
}
