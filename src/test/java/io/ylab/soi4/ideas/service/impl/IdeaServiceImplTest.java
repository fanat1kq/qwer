package io.ylab.soi4.ideas.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.ylab.soi4.ideas.model.Idea;
import io.ylab.soi4.ideas.repository.IdeaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IdeaServiceImplTest {

    @Mock
    private IdeaRepository ideaRepository;

    @InjectMocks
    private IdeaServiceImpl ideaService;

    @Test
    @DisplayName("Creating an idea. Should call the save method of the repository and return the idea when created")
    void testCreateIdea_shouldCallRepositorySaveAndReturnIdea() {
        Idea idea = new Idea();
        when(ideaRepository.save(idea)).thenReturn(idea);

        Idea createdIdea = ideaService.create(idea);

        verify(ideaRepository, times(1)).save(idea);
        assertEquals(idea, createdIdea);
    }
}