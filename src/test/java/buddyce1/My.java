package buddyce1;

import java.util.List;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.junit.Test;

import cn.hutool.core.util.RandomUtil;

public class My {

	@Test
	public void Test() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		
		TaskService taskService = processEngine.getTaskService();
		
		TaskQuery createTaskQuery = taskService.createTaskQuery();
		createTaskQuery.orderByTaskAssignee().list();
		
	}
}
