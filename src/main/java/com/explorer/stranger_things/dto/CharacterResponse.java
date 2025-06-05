package com.explorer.stranger_things.dto;

import lombok.Data;

import java.util.List;

@Data
public class CharacterResponse {
    private List<CharacterDto> data;
}