package io.ylab.soi4.ideas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository interface for all entities. Provides basic CRUD operations and specification
 * support for interacting with the database.
 *
 * @param <E> the type of the entity
 * @param <I> the type of the entity's identifier
 */
@NoRepositoryBean
public interface BaseRepository<E, I> extends JpaRepository<E, I>,
    JpaSpecificationExecutor<E> {

}