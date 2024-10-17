package io.ylab.soi4.ideas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.soi4.ideas.dto.UploadedFileInfo;
import io.ylab.soi4.ideas.model.File;
import io.ylab.soi4.ideas.service.CamundaApiService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(FilesController.class)
class FilesControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    protected ObjectMapper mapper;
    @MockBean
    private CamundaApiService camundaApiService;

    @Test
    @DisplayName("Test request to receive uploaded file info")
    void getAutoTest() throws Exception {
        List<UploadedFileInfo> request = List.of(
            new UploadedFileInfo("fileName", "filePath", "text/plain", 1L, true));

        String camundaId = "123";
        String expectedResponse = "Task completed successfully";
        Mockito.when(camundaApiService.completeRecentUserTask(
            Mockito.eq(camundaId),
            Mockito.any(Map.class))
        ).thenReturn(expectedResponse);

        mvc.perform(post("/files/{camunda_id}", camundaId)
                // .header(HttpHeaders.AUTHORIZATION, getValidCredentialsForManager())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request)))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }
}