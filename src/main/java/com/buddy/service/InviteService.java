package com.buddy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.buddy.dao.InviteDao;
import com.buddy.entity.Invite;
import com.buddy.entity.User;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;

@Service
public class InviteService {
	
	@Autowired
	InviteDao inviteDao;
	
	@Autowired
	UserService userService;
	
	/*
	 * 以下为工作流需要用到的service
	 */
	@Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;

	/*
	 * 邀请机制
	 */
	public List<String> invite(){
		
		List<String> list = new ArrayList<>();
		
		/*
		 * 注册链接
		 */
		String urlStr = RandomUtil.randomString(16)+".cn";
		
		list.add(urlStr);
		
		return list;
		
	}
	

	/*
	 * 等级机制
	 * id:用户id
	 */
	public void level(int id) {
		
		
		User user = userService.findById(id);
		List<User> users = userService.findAll();
		
		String pinviteCode = user.getPinviteCode();
		if(null==pinviteCode) {
			//如果为空说明是自己注册。没被邀请
			return;
		}
		else 
		{
		//获取被邀请的邀请码，遍历所有用户，获取用户自身的邀请码，如果被邀请的邀请码与用户自身邀请码相等，说明是一级用户
		for(User u:users) {
			if(pinviteCode.equals(u.getMyinviteCode())) {
				int pid1 = u.getId();
				int id1 = user.getId();
				Invite invite = new Invite();
				invite.setAuthorId(id1);
				invite.setPid(pid1);
				invite.setLevel(1);
				inviteDao.save(invite);
				}
			}
		//获取一级用户自身的邀请码,然后查询出一级用户下的用户，根据id大小排序，id最小的为一级用户父用户的二级用户，设置二级用户
		String myinviteCode = user.getMyinviteCode();
		//根据一级用户自身的邀请码作为被邀请码遍历出他所有被他邀请的人
		List<User> list = userService.findByPinviteCode(myinviteCode);
		//如果为空，说明一级用户没去邀请别人了
		if(null==list) {
			return;
		}else {
			//创建一个一级菜单所有邀请的用户的人员Id的集合
			List<Integer> ids =	new ArrayList<Integer>();
			//否则一级用户邀请了别人
			for(User u_1:list) {
				int idTemp = u_1.getId();
				ids.add(idTemp);
			}
			//对集合id进行排序，最小的为其用户的二级用户
			Collections.sort(ids);
			//得到二级用户id
			Integer idForSecond = ids.get(0);
			//获取一级用户父邀请码，查询父用户
			String pinviteCode2 = user.getPinviteCode();
			//查询出父用户
			User userF = userService.findByMyinviteCode(pinviteCode2);
			int idF = userF.getId();
			Invite invite = new Invite();
			invite.setAuthorId(idF);
			invite.setId(idForSecond);
			invite.setLevel(2);
			inviteDao.save(invite);
		}
		}
	}
	
	
	/*
	 * id是user的id
	 */
	public String reward(int id) {
		
		/*
		 * user的id已经在启动流程的时候传入了，由于工作流的流程定义还没完成，所以暂定id已经传入
		 */
		
		String sId = id+"";
		/*
		 * 根据userid获取工作流act_ru_task表中的ID
		 */
		TaskQuery taskQuery = taskService.createTaskQuery();
		List<Task> tasks = taskQuery.taskAssignee(sId).list();
		Task task = tasks.get(0);
		String taskID = task.getId();
		
		taskService.complete(taskID);
		
		
		/*
		 * 完成任务后根据用户id查询历史任务
		 */
		List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
		.processDefinitionId(taskID)
		.list();
		Date endTime = list.get(0).getEndTime();
		if(null==endTime) {
			return null;
		}else {
			User user = userService.findById(id);
			String pinviteCode = user.getPinviteCode();
			if(null==pinviteCode) {
				return null;
			}else {
				user.getId();
			}
			}
		
		
		return null;
		
		
		
		
		
	}
		
	
}
