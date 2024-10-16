package io.ylab.soi4.ideas.service;

import java.util.Map;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;

/**
 * Interface for accessing camundaApi via java-code
 */
public interface CamundaApiService {

    /**
     * Method for deploying Camunda process via java-code.
     *
     * @param processDefinitionKey String value of process-id
     * @Return String value of processInstanceId
     */
    String startProcess(String processDefinitionKey);

    /**
     * Method for deploying Camunda process via java-code.
     *
     * @param processInstanceId your processInstanceId
     * @param vars              your variables, which you want to deliver to user task
     * @Return String value of recent taskId
     */
    String completeRecentUserTask(String processInstanceId, Map<String, Object> vars);

}