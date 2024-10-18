package io.ylab.soi4.ideas.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import io.ylab.soi4.ideas.TestcontainersConfiguration;
import io.ylab.soi4.ideas.model.Idea;
import io.ylab.soi4.ideas.model.IdeaStatus;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@ActiveProfiles("test")
class IdeaRepositoryIntegrationTest {

    @Autowired
    private IdeaRepository ideaRepository;

    private Idea testIdea;

    @BeforeEach
    void setUp() {
        ideaRepository.deleteAll();
        testIdea = Idea.builder()
            .title("Test Idea")
            .annotation("Test Annotation")
            .description("Test Description")
            .userId(1L)
            .status(IdeaStatus.MODIFICATION)
            .likes(0L)
            .dislikes(0L)
            .isActive(true)
            .build();
    }

    @Test
    @DisplayName("Saving an idea. Returns the saved idea when the idea is saved.")
    void testSaveIdea_shouldReturnSavedIdea_whenIdeaIsSaved() {
        Idea savedIdea = ideaRepository.save(testIdea);

        assertThat(savedIdea).isNotNull();
        assertThat(savedIdea.getIdeaId()).isNotNull();
        assertThat(savedIdea.getTitle()).isEqualTo("Test Idea");
    }

    @Test
    @DisplayName("Saving an idea with invalid data. "
        + "Throws an exception when trying to save an idea with null in required fields.")
    void testSaveIdea_withInvalidData_shouldThrowException() {
        Idea invalidIdea = Idea.builder()
            .title(null)
            .annotation(null)
            .description(null)
            .userId(null)
            .status(IdeaStatus.MODIFICATION)
            .likes(0L)
            .dislikes(0L)
            .isActive(true)
            .build();

        assertThatThrownBy(() -> ideaRepository.save(invalidIdea))
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Finding an idea. Returns the idea when it exists.")
    void testFindIdeaById_shouldReturnIdea_whenIdeaExists() {
        Idea savedIdea = ideaRepository.save(testIdea);

        Optional<Idea> foundIdea = ideaRepository.findById(savedIdea.getIdeaId());

        assertThat(foundIdea).isPresent();
        assertThat(foundIdea.get().getTitle()).isEqualTo("Test Idea");
    }

    @Test
    @DisplayName("Finding an idea by a non-existent identifier. "
        + "Returns an empty value when searching for a non-existent idea.")
    void testFindIdeaById_shouldReturnEmpty_whenIdeaDoesNotExist() {
        Optional<Idea> foundIdea = ideaRepository.findById(999L);

        assertThat(foundIdea).isNotPresent();
    }

    @Test
    @DisplayName("Updating an idea. Returns the updated idea when the idea is updated.")
    void testUpdateIdea_shouldReturnUpdatedIdea_whenIdeaIsUpdated() {
        Idea savedIdea = ideaRepository.save(testIdea);
        savedIdea.setTitle("Updated Idea");

        Idea updatedIdea = ideaRepository.save(savedIdea);

        assertThat(updatedIdea.getTitle()).isEqualTo("Updated Idea");
    }

    @Test
    @DisplayName("Deleting an idea. Does not return it when the idea is deleted.")
    void testDeleteIdea_shouldNotReturnIdea_whenIdeaIsDeleted() {
        Idea savedIdea = ideaRepository.save(testIdea);

        ideaRepository.deleteById(savedIdea.getIdeaId());

        Optional<Idea> deletedIdea = ideaRepository.findById(savedIdea.getIdeaId());
        assertThat(deletedIdea).isNotPresent();
    }

    @Test
    @DisplayName("Deleting a non-existent idea. "
        + "Does not throw an exception when trying to delete a non-existent idea.")
    void testDeleteIdea_shouldNotThrowException_whenIdeaDoesNotExist() {
        assertThatCode(() -> ideaRepository.deleteById(999L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Finding all ideas. Returns all ideas when multiple ideas exist.")
    void testFindAllIdeas_shouldReturnAllIdeas_whenMultipleIdeasExist() {
        ideaRepository.save(testIdea);
        Idea anotherIdea = Idea.builder()
            .title("Another Idea")
            .annotation("Another Annotation")
            .description("Another Description")
            .userId(2L)
            .status(IdeaStatus.MODIFICATION)
            .likes(5L)
            .dislikes(1L)
            .isActive(true)
            .build();
        ideaRepository.save(anotherIdea);

        Iterable<Idea> ideas = ideaRepository.findAll();

        assertThat(ideas).hasSize(2);
    }
}