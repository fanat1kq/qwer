package io.ylab.soi4.ideas.controller;

import io.ylab.soi4.ideas.dto.UploadedFileInfo;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controllers to files requests
 */
@RestController
@RequestMapping("/files")
public class FilesController {

    /**
     * Inner endpoint for receiving uploaded file info
     *
     * @param request file info
     * @return response entity
     */
    @PutMapping
    public ResponseEntity<Void> receiveData(@RequestBody List<UploadedFileInfo> request) {
        // TODO вызов idea сервиса по загруженным файлам
        return ResponseEntity.ok().build();
    }
}