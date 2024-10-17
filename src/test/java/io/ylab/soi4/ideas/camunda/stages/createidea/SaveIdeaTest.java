package io.ylab.soi4.ideas.camunda.stages.createidea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.ylab.soi4.ideas.dto.UploadedFileInfo;
import io.ylab.soi4.ideas.model.File;
import io.ylab.soi4.ideas.model.Idea;
import io.ylab.soi4.ideas.service.FileService;
import io.ylab.soi4.ideas.service.IdeaService;
import java.util.List;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SaveIdeaTest {

    @Mock
    private IdeaService ideaService;

    @Mock
    private FileService fileService;

    @Mock
    private DelegateExecution delegateExecution;

    private SaveIdea saveIdea;

    @BeforeEach
    void setUp() {
        saveIdea = new SaveIdea(ideaService, fileService);
    }

    @Test
    @DisplayName("Executing Java delegate method. Verifies saving of idea and files through service calls")
    void testExecute() throws Exception {
        // Arrange
        when(delegateExecution.getVariable("title")).thenReturn("Test Title");
        when(delegateExecution.getVariable("annotation")).thenReturn("Test Annotation");
        when(delegateExecution.getVariable("description")).thenReturn("Test Description");

        UploadedFileInfo uploadedFile = new UploadedFileInfo();
        uploadedFile.setFileName("file1.txt");
        uploadedFile.setFilePath("/path/to/file1.txt");
        uploadedFile.setContentType("text/plain");
        uploadedFile.setFileSize(100L);
        uploadedFile.setIsActive(true);

        List<UploadedFileInfo> uploadedFiles = List.of(uploadedFile);
        when(delegateExecution.getVariable("filesInfo")).thenReturn(uploadedFiles);

        Idea createdIdea = new Idea();
        createdIdea.setIdeaId(1L);
        when(ideaService.create(any(Idea.class))).thenReturn(createdIdea);

        // Act
        saveIdea.execute(delegateExecution);

        // Assert
        verify(ideaService, times(1)).create(any(Idea.class));
        verify(fileService, times(1)).create(any(File.class));
    }

    @Test
    @DisplayName("Converting uploaded file list to File object list. Ensures correct property mapping between lists")
    void testConvertToFileList() {
        // Arrange
        UploadedFileInfo uploadedFile = new UploadedFileInfo();
        uploadedFile.setFileName("file1.txt");
        uploadedFile.setFilePath("/path/to/file1.txt");
        uploadedFile.setContentType("text/plain");
        uploadedFile.setFileSize(50L);
        uploadedFile.setIsActive(true);

        List<UploadedFileInfo> uploadedFiles = List.of(uploadedFile);

        // Act
        List<File> files = saveIdea.convertToFileList(uploadedFiles);

        // Assert
        assertEquals(1, files.size());
        assertEquals("file1.txt", files.get(0).getFileName());
        assertEquals("/path/to/file1.txt", files.get(0).getFilePath());
        assertEquals("text/plain", files.get(0).getContentType());
        assertEquals(50L, files.get(0).getFileSize());
        assertTrue(files.get(0).getIsActive());
    }
}