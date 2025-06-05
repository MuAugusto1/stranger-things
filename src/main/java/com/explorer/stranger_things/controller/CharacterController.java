package com.explorer.stranger_things.controller;

import com.explorer.stranger_things.dto.CharacterDto;
import com.explorer.stranger_things.service.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping
    public List<CharacterDto> getCharacters(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getCharacters(page, size);
    }

    @GetMapping("/search")
    public List<CharacterDto> getCharactersByName(@RequestParam String name) {
        return service.getCharactersByName(name);
    }

    @GetMapping("/filter-by-gender")
    public List<CharacterDto> getCharactersByGender(
            @RequestParam String gender,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getCharactersByGender(gender, page, size);
    }

    @GetMapping("/top-5")
    public List<CharacterDto> getTop5Characters() {
        return service.getTop5CharactersByEpisodes();
    }
}