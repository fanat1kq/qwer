package io.ylab.soi4.ideas.repository;

import io.ylab.soi4.ideas.model.File;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with the "File" entity. Provides methods for performing CRUD operations
 */
@Repository
public interface FileRepository extends BaseRepository<File, Long> {

}