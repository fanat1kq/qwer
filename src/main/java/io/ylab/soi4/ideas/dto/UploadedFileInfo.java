package io.ylab.soi4.ideas.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Dto to store saved file info.
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UploadedFileInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** The name of the file. */
    String fileName;

    /** The file path where the file is stored. */
    String filePath;

    /** The content type of the file. */
    String contentType;

    /** The size of the file. */
    Long fileSize;

    /** Indicates whether the file is currently active or not. */
    Boolean isActive;
}
