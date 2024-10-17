package io.ylab.soi4.ideas.controller;

import io.ylab.soi4.ideas.dto.UploadedFileInfo;
import io.ylab.soi4.ideas.service.CamundaApiService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controllers to files requests
 */
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FilesController {

    private final CamundaApiService camundaApiService;

    /**
     * Intercepts a request to add file info and completes a task in Camunda.
     *
     * @param camunda_id Camunda process ID
     * @param files      list of file metadata
     */
    @PostMapping(value = "/{camunda_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFilesInfo(@PathVariable String camunda_id,
        @RequestBody List<UploadedFileInfo> files) {
        Map<String, Object> vars = new HashMap<>();

        vars.put("filesInfo", files);

        return ResponseEntity.ok().body(camundaApiService.completeRecentUserTask(camunda_id, vars));
    }
}