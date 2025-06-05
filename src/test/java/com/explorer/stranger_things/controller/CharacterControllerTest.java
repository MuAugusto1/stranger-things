package com.explorer.stranger_things.controller;


import com.explorer.stranger_things.dto.CharacterDto;
import com.explorer.stranger_things.service.CharacterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharacterController.class)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetCharacters() throws Exception {
        CharacterDto character = new CharacterDto();
        character.setName("Eleven");
        character.setGender("Female");

        when(service.getCharacters(1, 2)).thenReturn(List.of(character));

        mockMvc.perform(get("/characters")
                        .param("page", "1")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Eleven"));
    }

    @Test
    void testGetCharactersByName() throws Exception {
        CharacterDto character = new CharacterDto();
        character.setName("Mike");
        character.setGender("Male");

        when(service.getCharactersByName("Mike")).thenReturn(List.of(character));

        mockMvc.perform(get("/characters/search")
                        .param("name", "Mike"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Mike"));
    }

    @Test
    void testGetCharactersByGender() throws Exception {
        CharacterDto character = new CharacterDto();
        character.setName("Lucas");
        character.setGender("Male");

        when(service.getCharactersByGender("Male", 1, 3)).thenReturn(List.of(character));

        mockMvc.perform(get("/characters/gender")
                        .param("gender", "Male")
                        .param("page", "1")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gender").value("Male"));
    }

    @Test
    void testGetTop5Characters() throws Exception {
        CharacterDto character = new CharacterDto();
        character.setName("Dustin");
        character.setAppearsInEpisodes(List.of("1", "2", "3"));

        when(service.getTop5CharactersByEpisodes()).thenReturn(List.of(character));

        mockMvc.perform(get("/characters/top5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Dustin"));
    }
}
