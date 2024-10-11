package io.ylab.soi4.ideas.repository;

import io.ylab.soi4.ideas.model.Idea;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with the "Idea" entity. Provides methods for performing CRUD operations
 * and interacting with the database for objects of type Idea.
 */
@Repository
public interface IdeaRepository extends BaseRepository<Idea, Long> {

}