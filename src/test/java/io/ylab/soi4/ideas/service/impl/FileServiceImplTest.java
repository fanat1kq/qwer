package io.ylab.soi4.ideas.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.ylab.soi4.ideas.model.File;
import io.ylab.soi4.ideas.repository.FileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    @Test
    @DisplayName("Creating a File. Should call save method of the File repository when creating a file")
    void testCreateFile_shouldCallRepositorySaveMethod() {
        File file = new File();

        fileService.create(file);

        verify(fileRepository, times(1)).save(file);
    }
}