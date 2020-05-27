package com.buddy.controller;

import java.util.HashMap;
import java.util.List;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.entity.vo.Result;
import com.buddy.entity.vo.ResultCode;

@RestController
@RequestMapping(value = "/expense")
public class FlowableController {
	
	@Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    
    
    /*
     * 启动流程实例
     */
    @PostMapping("/start/{userId}/{purchaseOrderId}")
    public void startFlow(@PathVariable String userId, @PathVariable String purchaseOrderId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("purchaseOrderId", purchaseOrderId);
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("OrderApproval", map);
        String processId = processInstance.getId();
        String name = processInstance.getName();
        System.out.println(processId + ":" + name);
    }
    


    /*
     * 获取用户任务
     * userID 用户id
     */
    @RequestMapping(value = "/getTasks/{userId}")
    public Object list(@PathVariable(value = "userId")String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
        for (Task task : tasks) {
            System.out.println(task.getProcessDefinitionId());
        }
        return tasks.toArray().toString();
    }
    
    /*
     * 审批通过
     */
    @PostMapping(value = "/success/{taskId}")
    public Result success(@PathVariable(value = "taskId") String taskId) {
    	
    	Task task = taskService.createTaskQuery().taskId("taskId").singleResult();
    	if(task == null) {
    		return new Result(ResultCode.FAIL);
    	}
    	//通过审核
    	HashMap<String, Object> map = new HashMap<>();
    	return null;
    	
    }
    
    /*
     * 完成任务
     */
    @RequestMapping(value = "/finishTasks/{userId}")
    public void finish(@PathVariable(value = "userId")String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
        Task task = tasks.get(0);
        String taskId = task.getId();
        taskService.complete(taskId);
        
        
    }

}
