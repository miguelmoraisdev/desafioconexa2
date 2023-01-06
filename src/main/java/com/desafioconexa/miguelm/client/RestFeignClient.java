package com.desafioconexa.miguelm.client;

import com.desafioconexa.miguelm.dto.FilmRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "apidesafio", url = "http://localhost:8081/")
public interface RestFeignClient {

    @GetMapping(value = "film/lukeskywalker", consumes="application/json")
    public Page<FilmRequest> getFilmsList();
}
