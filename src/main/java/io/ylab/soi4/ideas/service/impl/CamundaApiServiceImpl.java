package io.ylab.soi4.ideas.service.impl;

import io.ylab.soi4.ideas.service.CamundaApiService;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.camunda.bpm.engine.task.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CamundaApiServiceImpl implements CamundaApiService {

    private final ProcessEngine processEngine;

    @Bean
    @Primary
    public ProcessEngine getProcessEngine() {
        return ProcessEngines.getDefaultProcessEngine();
    }

    public String startProcess(String processDefinitionKey) {
        ProcessInstantiationBuilder processInstance = processEngine.getRuntimeService()
            .createProcessInstanceByKey(processDefinitionKey);
        System.out.println("Process: " + processDefinitionKey + " is started");
        return processInstance.execute().getProcessInstanceId();
    }

    public String completeRecentUserTask(String processInstanceId, Map<String, Object> vars) {
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .list();
        Task task = tasks.getFirst();
        taskService.complete(task.getId(), vars);
        System.out.println("Task completed with id " + task.getId());
        return processInstanceId;
    }
}