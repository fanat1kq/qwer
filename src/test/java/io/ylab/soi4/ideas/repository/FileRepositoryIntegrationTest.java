package io.ylab.soi4.ideas.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


import io.ylab.soi4.ideas.model.File;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.utility.TestcontainersConfiguration;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class IdeaFileRepositoryIntegrationTest {

    @Autowired
    private FileRepository fileRepository;

    private File testFile;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5432/files_test");
        registry.add("spring.datasource.username", () -> "files_test_user");
        registry.add("spring.datasource.password", () -> "password");
    }

    @BeforeEach
    void setUp() {
        fileRepository.deleteAll();
        testFile = File.builder().ideaId(1L).fileId(1l).filePath("/Test").fileName("Test name")
            .contentType("Test type").fileSize(123L).isActive(true).build();
    }

    @Test
    @DisplayName("Saving an idea. Returns the saved idea when the idea is saved.")
    void testSaveIdea_shouldReturnSavedIdea_whenIdeaIsSaved() {
        File savedFile = fileRepository.save(testFile);

        assertThat(savedFile).isNotNull();
        assertThat(savedFile.getIdeaId()).isNotNull();
        assertThat(savedFile.getFileName()).isEqualTo("Test name");
    }

    @Test
    @DisplayName("Saving an idea with invalid data. "
        + "Throws an exception when trying to save an idea with null in required fields.")
    void testSaveIdea_withInvalidData_shouldThrowException() {
        File invalidIdea =File.builder().ideaId(1L).fileId(1l).filePath("/Test").fileName("Test name")
            .contentType("Test type").fileSize(123L).isActive(false).build();

        assertThatThrownBy(() -> fileRepository.save(invalidIdea)).isInstanceOf(
            DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Finding an idea. Returns the idea when it exists.")
    void testFindIdeaById_shouldReturnIdea_whenIdeaExists() {
        File savedIdea = fileRepository.save(testFile);

        Optional<File> foundIdea = fileRepository.findById(savedIdea.getIdeaId());

        assertThat(foundIdea).isPresent();
        assertThat(foundIdea.get().getContentType()).isEqualTo("Test type");
    }

    @Test
    @DisplayName("Finding an idea by a non-existent identifier. "
        + "Returns an empty value when searching for a non-existent idea.")
    void testFindIdeaById_shouldReturnEmpty_whenIdeaDoesNotExist() {
        Optional<File> foundIdea = fileRepository.findById(999L);

        assertThat(foundIdea).isNotPresent();
    }

    @Test
    @DisplayName("Updating an idea. Returns the updated idea when the idea is updated.")
    void testUpdateIdea_shouldReturnUpdatedIdea_whenIdeaIsUpdated() {
        File savedIdea = fileRepository.save(testFile);
        savedIdea.setFileName("Updated file name");

        File updatedIdea = fileRepository.save(savedIdea);

        assertThat(updatedIdea.getFileName()).isEqualTo("Updated file name");
    }

    @Test
    @DisplayName("Deleting an idea. Does not return it when the idea is deleted.")
    void testDeleteIdea_shouldNotReturnIdea_whenIdeaIsDeleted() {
        File savedFile = fileRepository.save(testFile);

        fileRepository.deleteById(savedFile.getIdeaId());

        Optional<File> deletedFile = fileRepository.findById(savedFile.getIdeaId());
        assertThat(deletedFile).isNotPresent();
    }

    @Test
    @DisplayName("Deleting a non-existent idea. "
        + "Does not throw an exception when trying to delete a non-existent idea.")
    void testDeleteIdea_shouldNotThrowException_whenIdeaDoesNotExist() {
        assertThatCode(() -> fileRepository.deleteById(333L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Finding all ideas. Returns all ideas when multiple ideas exist.")
    void testFindAllIdeas_shouldReturnAllIdeas_whenMultipleIdeasExist() {
        fileRepository.save(testFile);
        File anotherFile = File.builder().ideaId(2L).fileId(1l).filePath("/Another/path").fileName("Another test name")
            .contentType("Another test type").fileSize(456L).build();
        fileRepository.save(anotherFile);

        Iterable<File> files = fileRepository.findAll();

        assertThat(files).hasSize(2);
    }
}