package com.explorer.stranger_things.service;

import com.explorer.stranger_things.client.StrangerThingsClient;
import com.explorer.stranger_things.dto.CharacterDto;
import com.explorer.stranger_things.dto.CharacterResponse;
import com.explorer.stranger_things.exception.CharacterNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {

    @Mock
    private StrangerThingsClient client;

    @InjectMocks
    private CharacterService service;

    public CharacterServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCharactersByNameSuccess() {
        var character = new CharacterDto();
        character.setName("Eleven");

        var response = new CharacterResponse();
        response.setData(List.of(character));

        when(client.getCharacters(1, 1000)).thenReturn(response);

        var result = service.getCharactersByName("eve");
        assertEquals(1, result.size());
        assertEquals("Eleven", result.get(0).getName());
    }

    @Test
    void testGetCharactersByNameNotFound() {
        var response = new CharacterResponse();
        response.setData(List.of());

        when(client.getCharacters(1, 1000)).thenReturn(response);

        assertThrows(CharacterNotFoundException.class, () -> {
            service.getCharactersByName("Will");
        });
    }

    @Test
    void testGetCharactersByGender() {
        var character1 = new CharacterDto();
        character1.setName("Mike");
        character1.setGender("Male");

        var character2 = new CharacterDto();
        character2.setName("Eleven");
        character2.setGender("Female");

        var response = new CharacterResponse();
        response.setData(List.of(character1, character2));

        when(client.getCharacters(1, 10)).thenReturn(response);

        var result = service.getCharactersByGender("Female", 1, 10);
        assertEquals(1, result.size());
        assertEquals("Eleven", result.get(0).getName());
    }

    @Test
    void testGetTop5Characters() {
        var character1 = new CharacterDto();
        character1.setName("Mike");
        character1.setAppearsInEpisodes(List.of("1", "2"));

        var character2 = new CharacterDto();
        character2.setName("Eleven");
        character2.setAppearsInEpisodes(List.of("1", "2", "3", "4"));

        var character3 = new CharacterDto();
        character3.setName("Dustin");
        character3.setAppearsInEpisodes(List.of("1", "2", "3"));

        var response = new CharacterResponse();
        response.setData(List.of(character1, character2, character3));

        when(client.getCharacters(1, 1000)).thenReturn(response);

        var result = service.getTop5CharactersByEpisodes();
        assertEquals(3, result.size());
        assertEquals("Eleven", result.get(0).getName()); // mais episódios
    }
}