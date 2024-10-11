package io.ylab.soi4.ideas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents the "Files" entity created by a user. "Files" table in the database.
 */
@Getter
@Setter
@Entity
@Table(name = "files", schema = "ideas")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {

    /**
     * The unique identifier for the file of idea. Automatically generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idea_file_id")
    private Long ideaFileId;

    /**
     * The ID of the idea.
     */
    @Column(name = "idea_id")
    private Long ideaId;

    /**
     * The ID for the file.
     */
    @Column(name = "file_id")
    private Long fileId;

    /**
     * The path of the file.
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * The name of the file.
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * The type of the content.
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * The description of the idea.
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * Indicates whether the file is active.
     */
    @Column(name = "is_active")
    private Boolean isActive;
}