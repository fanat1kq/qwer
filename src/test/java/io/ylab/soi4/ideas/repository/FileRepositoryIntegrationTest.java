package io.ylab.soi4.ideas.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import io.ylab.soi4.ideas.TestcontainersConfiguration;
import io.ylab.soi4.ideas.model.File;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class FileRepositoryIntegrationTest {

    @Autowired
    private FileRepository fileRepository;

    private File testFile;

    @BeforeEach
    void setUp() {
        fileRepository.deleteAll();
        testFile = File.builder().ideaId(1L).fileId(3L).filePath("/Test").fileName("Test name")
            .contentType("Test type").fileSize(123L).isActive(true).build();
    }

    @Test
    @DisplayName("Saving an file. Returns the saved file when the file is saved.")
    void testSaveFile_shouldReturnSavedFile_whenFileIsSaved() {
        File savedFile = fileRepository.save(testFile);

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(savedFile).isNotNull();
        softly.assertThat(savedFile.getIdeaId()).isNotNull();
        softly.assertThat(savedFile.getFileName()).isEqualTo("Test name");
        softly.assertAll();
    }

    @Test
    @DisplayName("Saving an file with invalid data. "
        + "Throws an exception when trying to save an file with null in required fields.")
    void testSaveFile_withInvalidData_shouldThrowException() {
        File invalidFile = File.builder().ideaId(0L).fileId(4L).filePath(null).fileName(null)
            .contentType(null).fileSize(0L).isActive(false).build();

        assertThatThrownBy(() -> fileRepository.save(invalidFile)).isInstanceOf(
            DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Finding an file. Returns the file when it exists.")
    void testFindFileById_shouldReturnFile_whenFileExists() {
        File savedFile = fileRepository.save(testFile);

        Optional<File> foundFile = fileRepository.findById(savedFile.getIdeaId());

        assertThat(foundFile).isPresent();
        assertThat(foundFile.get().getContentType()).isEqualTo("Test type");
    }

    @Test
    @DisplayName("Finding an file by a non-existent identifier. "
        + "Returns an empty value when searching for a non-existent file.")
    void testFindFileById_shouldReturnEmpty_whenFileDoesNotExist() {
        Optional<File> foundFile = fileRepository.findById(999L);

        assertThat(foundFile).isNotPresent();
    }

    @Test
    @DisplayName("Updating an file. Returns the updated file when the file is updated.")
    void testUpdateFile_shouldReturnUpdatedFile_whenFileIsUpdated() {
        File savedFile = fileRepository.save(testFile);
        savedFile.setFileName("Updated file name");

        File updatedFile = fileRepository.save(savedFile);

        assertThat(updatedFile.getFileName()).isEqualTo("Updated file name");
    }

    @Test
    @DisplayName("Deleting an file. Does not return it when the file is deleted.")
    void testDeleteFile_shouldNotReturnFile_whenFileIsDeleted() {
        File savedFile = fileRepository.save(testFile);

        fileRepository.deleteById(savedFile.getIdeaId());

        Optional<File> deletedFile = fileRepository.findById(savedFile.getIdeaId());
        assertThat(deletedFile).isNotPresent();
    }

    @Test
    @DisplayName("Deleting a non-existent file. "
        + "Does not throw an exception when trying to delete a non-existent file.")
    void testDeleteFile_shouldNotThrowException_whenFileDoesNotExist() {
        assertThatCode(() -> fileRepository.deleteById(333L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Finding all files. Returns all files when multiple files exist.")
    void testFindAllFiles_shouldReturnAllFiles_whenMultipleFilesExist() {
        fileRepository.save(testFile);
        File anotherFile = File.builder().ideaId(2L).fileId(5L).filePath("/Another/path")
            .fileName("Another test name")
            .contentType("Another test type").fileSize(456L).isActive(false).build();
        fileRepository.save(anotherFile);

        Iterable<File> files = fileRepository.findAll();

        assertThat(files).hasSize(2);
    }
}