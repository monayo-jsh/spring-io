package com.example.uploadingfiles.storage;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Paths;
import java.util.stream.Stream;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@SpringBootTest
class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageService storageService;

    @Test
    void shouldListAllFiles() throws Exception {
        //given
        given(this.storageService.loadAll())
            .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/"));

        //then
        resultActions.andExpect(status().isOk())
                     .andExpect(model().attribute("files", Matchers.contains("http://localhost/files/first.txt", "http://localhost/files/second.txt")));
    }

    @Test
    void shouldSaveUploadedFile() throws Exception {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("file", "text.txt", "text/plain", "Spring Framework".getBytes());

        //when
        ResultActions resultActions = this.mockMvc.perform(multipart("/").file(multipartFile));

        //then
        resultActions.andExpect(status().isFound())
                     .andExpect(header().string("Location", "/"));
    }

    @Test
    void should404WhenMissingFile() throws Exception {
        //given
        given(this.storageService.loadAsResource("text.txt"))
            .willThrow(StorageFileNotFoundException.class);

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/files/text.txt"));

        //then
        resultActions.andExpect(status().isNotFound());
    }
}