package io.ylab.soi4.ideas.dto;

/**
 * Dto to store saved file info.
 */
public record UploadedFileInfo(
    String fileName,
    String filePath,
    String contentType,
    Long fileSize,
    Boolean isActive) {
}
