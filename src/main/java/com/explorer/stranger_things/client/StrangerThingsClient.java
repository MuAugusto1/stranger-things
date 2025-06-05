package com.explorer.stranger_things.client;

import com.explorer.stranger_things.dto.CharacterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "strangerThingsClient", url = "https://stranger-things-api.fly.dev/api/v1")
public interface StrangerThingsClient {

    @GetMapping("/characters")
    CharacterResponse getCharacters(
            @RequestParam("page") int page,
            @RequestParam("perPage") int perPage
    );
}