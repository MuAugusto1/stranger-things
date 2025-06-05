package com.explorer.stranger_things.service;

import com.explorer.stranger_things.client.StrangerThingsClient;
import com.explorer.stranger_things.dto.CharacterDto;
import com.explorer.stranger_things.dto.Gender;
import com.explorer.stranger_things.exception.CharacterNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final StrangerThingsClient client;

    public CharacterService(StrangerThingsClient client) {
        this.client = client;
    }

    public List<CharacterDto> getCharacters(int page, int size) {
        return client.getCharacters(page, size).getData();
    }

    public List<CharacterDto> getCharactersByName(String name) {
        // Buscar todos os personagens (vamos usar um tamanho alto para pegar todos)
        List<CharacterDto> allCharacters = client.getCharacters(1, 1000).getData();

        List<CharacterDto> filtered = allCharacters.stream()
                .filter(c -> c.getName() != null && c.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        if (filtered.isEmpty()) {
            throw new CharacterNotFoundException("Nenhum personagem encontrado com o nome: " + name);
        }

        return filtered;
    }

    public List<CharacterDto> getCharactersByGender(String genderStr, int page, int size) {
        Gender gender = Gender.fromString(genderStr);

        List<CharacterDto> characters = client.getCharacters(page, size).getData();

        return characters.stream()
                .filter(c -> gender.name().equalsIgnoreCase(c.getGender()))
                .toList();
    }

    public List<CharacterDto> getTop5CharactersByEpisodes() {
        List<CharacterDto> all = client.getCharacters(1, 1000).getData();

        return all.stream()
                .sorted((a, b) -> Integer.compare(
                        b.getAppearsInEpisodes().size(),
                        a.getAppearsInEpisodes().size()))
                .limit(5)
                .toList();
    }
}
