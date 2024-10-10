package io.ylab.soi4.ideas.repository;

import io.ylab.soi4.ideas.model.Files;

/**
 * Repository for working with the "File" entity. Provides methods for performing CRUD operations
 */
@org.springframework.stereotype.Repository
public interface Repository extends BaseRepository<Files, Long> {

}