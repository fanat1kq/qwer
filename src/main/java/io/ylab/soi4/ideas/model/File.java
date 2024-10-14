package io.ylab.soi4.ideas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Column(name = "idea_id", nullable = false)
    private Long ideaId;

    /**
     * The ID for the file.
     */
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    /**
     * The Idea entity that this file is associated with.
     * Represents a many-to-one relationship.
     * Not updatable or insertable in this entity to avoid circular references.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idea_id", nullable = false, insertable = false, updatable = false)
    private Idea idea;

    /**
     * The path of the file.
     */
    @Column(name = "file_path", nullable = false)
    private String filePath;

    /**
     * The name of the file.
     */
    @Column(name = "file_name", nullable = false)
    private String fileName;

    /**
     * The type of the content.
     */
    @Column(name = "content_type", nullable = false)
    private String contentType;

    /**
     * The description of the idea.
     */
    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    /**
     * Indicates whether the file is active.
     */
    @Column(name = "is_active" , nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")   
    private Boolean isActive;
}