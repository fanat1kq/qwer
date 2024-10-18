package io.ylab.soi4.ideas.service;

import io.ylab.soi4.ideas.model.Idea;

/**
 * This interface defines the contract for creating ideas.
 */
public interface IdeaService {

    /**
     * Creates a new idea.
     *
     * @param idea the idea to be created
     * @return the created idea
     */
    Idea create(Idea idea);
}