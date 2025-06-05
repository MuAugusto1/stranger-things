package com.explorer.stranger_things.dto;

import lombok.Data;

import java.util.List;

@Data
public class CharacterDto {
    private String name;
    private String gender;
    private String hairColor;
    private String eyeColor;
    private String born;
    private List<String> appearsInEpisodes;
}