package io.ylab.soi4.ideas.camunda.stages.createidea;

import io.ylab.soi4.ideas.dto.UploadedFileInfo;
import io.ylab.soi4.ideas.model.File;
import io.ylab.soi4.ideas.model.Idea;
import io.ylab.soi4.ideas.service.FileService;
import io.ylab.soi4.ideas.service.IdeaService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * Delegate-class for operate last service-task in Camunda idea-create-process It should save
 * idea-entity to DB.
 */
@Component("saveIdea")
@RequiredArgsConstructor
public class SaveIdea implements JavaDelegate {

    private final IdeaService ideaService;
    private final FileService fileService;

    /**
     * Creates an idea and associated files in a BPMN process.
     *
     * @param delegateExecution BPMN execution context with necessary variables
     * @throws Exception if process execution fails
     */
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String title = (String) delegateExecution.getVariable("title");
        String annotation = (String) delegateExecution.getVariable("annotation");
        String description = (String) delegateExecution.getVariable("description");

        List<File> files = convertToFileList(
            (List<UploadedFileInfo>) delegateExecution.getVariable("filesInfo"));

        Idea idea = Idea.builder().title(title).annotation(annotation).description(description)
            .userId(1L).build(); // Необходимо получать user_id из токена

        Long ideaId = ideaService.create(idea).getIdeaId();

        for (File file : files) {
            file.setIdeaId(ideaId);
            file.setFileId(1L); // Необходимо будет удалить это поле из таблицы и сущности
            fileService.create(file);
        }
    }

    /**
     * Converts a list of {@link UploadedFileInfo} to {@link File} entities.
     *
     * @param uploadedFiles list of {@link UploadedFileInfo} to convert
     * @return list of {@link File} entities
     */
    public List<File> convertToFileList(List<UploadedFileInfo> uploadedFiles) {
        return uploadedFiles.stream().map(uploadedFileInfo -> {
            File file = new File();
            file.setFileName(uploadedFileInfo.getFileName());
            file.setFilePath(uploadedFileInfo.getFilePath());
            file.setContentType(uploadedFileInfo.getContentType());
            file.setFileSize(uploadedFileInfo.getFileSize());
            file.setIsActive(uploadedFileInfo.getIsActive());
            return file;
        }).collect(Collectors.toList());
    }
}