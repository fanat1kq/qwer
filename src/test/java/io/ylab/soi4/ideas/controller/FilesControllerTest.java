package io.ylab.soi4.ideas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.soi4.ideas.dto.UploadedFileInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

    @Test
    @DisplayName("Test request to receive uploaded file info")
    void getAutoTest() throws Exception {
        List<UploadedFileInfo> request = List.of(
            new UploadedFileInfo("fileName", "filePath",
                "text/plain", 1L, true));
        mvc.perform(put("/files")
                // .header(HttpHeaders.AUTHORIZATION, getValidCredentialsForManager())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request)))
            .andExpect(status().isOk());
    }
}