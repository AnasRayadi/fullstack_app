package com.rayadi.backend.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class MvcHelper {
    private final static ObjectMapper objectMapper = new ObjectMapper();
    public static ResultActions performPostRequest(String endpoint, Object data, MockMvc mockMvc) throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        return mockMvc.perform(post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)));
    }
    public static ResultActions performUpdateRequest(String endpoint, Object data, MockMvc mockMvc) throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        return mockMvc.perform(put(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)));
    }

}
