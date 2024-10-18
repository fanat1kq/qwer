package io.ylab.soi4.ideas.camunda.stages.createidea;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * Delegate-class for operate last service-task in Camunda idea-create-process
 * It should save idea-entity to DB.
 */
@Component("saveIdea")
public class SaveIdea implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Saving idea to DB...");
    }
}