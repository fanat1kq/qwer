package io.ylab.soi4.ideas.service;

import io.ylab.soi4.ideas.model.File;

/**
 * This interface defines the contract for creating files.
 */
public interface FileService {

    /**
     * Creates a new idea.
     *
     * @param file the file to be created
     */
    void create(File file);
}
