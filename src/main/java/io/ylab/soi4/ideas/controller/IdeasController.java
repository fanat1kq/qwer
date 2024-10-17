package io.ylab.soi4.ideas.controller;

import io.ylab.soi4.ideas.model.Idea;
import io.ylab.soi4.ideas.repository.FileRepository;
import io.ylab.soi4.ideas.repository.IdeaRepository;
import io.ylab.soi4.ideas.service.CamundaApiService;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controllers to ideas requests
 */
@RestController
@AllArgsConstructor
@RequestMapping("/create")
public class IdeasController {

    private final CamundaApiService camundaApiService;
    private final FileRepository fileRepository;
    private final IdeaRepository ideaRepository;

    /**
     * A method for intercepting a request from BFF and starting the process Camunda
     *
     * @return ResponseEntity<String> value of process-id
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createIdea() {
        return ResponseEntity.ok()
            .body(camundaApiService.startProcess("create-idea-process"));
    }

    /**
     * A method for intercepting a request from BFF and add title and annotation vars to
     * Camunda-process
     *
     * @param camunda_id process-id in Camunda context
     * @param idea       idea-entity
     */
    @PostMapping(value = "/{camunda_id}/title", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTitleAndAnnotationToIdea(@PathVariable String camunda_id,
        @RequestBody Idea idea) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("title", idea.getTitle());
        vars.put("annotation", idea.getAnnotation());
        return ResponseEntity.ok().body(camundaApiService.completeRecentUserTask(camunda_id, vars));
    }

    /**
     * A method for intercepting a request from BFF and add description var to Camunda-process
     *
     * @param camunda_id last id from task of process
     * @param idea       idea-entity
     */
    @PostMapping(value = "/{camunda_id}/description", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addDescription(@PathVariable String camunda_id,
        @RequestBody Idea idea) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("description", idea.getDescription());
        return ResponseEntity.ok().body(camundaApiService.completeRecentUserTask(camunda_id, vars));
    }

    /**
     * A method for intercepting a request from BFF and add fileInfo var to Camunda-process
     * TODO - need to modify!
     *
     * @param camunda_id last id from task of process
     * @param idea       idea-entity
     */
    @PostMapping(value = "/{camunda_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFilesInfo(@PathVariable String camunda_id,
        @RequestBody Idea idea) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("fileInfo", idea.getDescription());
        return ResponseEntity.ok().body(camundaApiService.completeRecentUserTask(camunda_id, vars));
    }
}