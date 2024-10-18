package io.ylab.soi4.ideas.service.impl;

import io.ylab.soi4.ideas.model.Idea;
import io.ylab.soi4.ideas.repository.IdeaRepository;
import io.ylab.soi4.ideas.service.IdeaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link IdeaService} interface. Provides the functionality to create ideas
 * using the underlying repository layer.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Idea create(Idea idea) {
        return ideaRepository.save(idea);
    }
}
