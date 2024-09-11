package com.example.app.controller;

import com.example.app.model.Link;
import com.example.app.model.dto.LinkCreateDTO;
import com.example.app.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/v1/link")
@RequiredArgsConstructor
@Tag(name = "Link Controller", description = "API для керування короткими URL-адресами")
public class LinkController {
    private final LinkService linkService;

    @PostMapping("/create")
    @Operation(summary = "Створення короткої URL", description = "Створює нову коротку URL-адресу на основі введеного посилання")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Коротке посилання успішно створено"),
            @ApiResponse(responseCode = "400", description = "Невірні дані для створення посилання")
    })
    public String createShortUrl(@RequestBody LinkCreateDTO linkCreateDTO) {
        return linkService.add(linkCreateDTO);
    }


    @GetMapping("/id/{id}")
    @Operation(summary = "Отримання посилання за ID", description = "Повертає повне посилання за його ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Посилання успішно знайдено"),
            @ApiResponse(responseCode = "404", description = "Посилання не знайдено",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class)))
    })
    public Link getLinkById(@PathVariable long id) {
        return linkService.findById(id);
    }


    @GetMapping("/redirect/{shortUrl}")
    @Operation(summary = "Редірект на повну URL", description = "Перенаправляє користувача на повне посилання на основі короткого URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "301", description = "Перенаправлення на повну URL"),
            @ApiResponse(responseCode = "404", description = "Коротке посилання не знайдено", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class)))
    })
    @ResponseStatus(code = HttpStatus.MOVED_PERMANENTLY)
    public String redirectToFullUrl(@PathVariable String shortUrl) {
        return linkService.getFullUrl(shortUrl);
    }



    @PostMapping("/validity/extend/{id}")
    @Operation(summary = "Подовження терміну дії посилання", description = "Подовжує термін дії існуючого короткого URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Термін дії посилання подовжено"),
            @ApiResponse(responseCode = "404", description = "Посилання не знайдено")
    })
    public String extendLinkValidity(@PathVariable long id) {
        linkService.extendLinkValidity(id);
        return "Link validity extended successfully";
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Видалення посилання", description = "Видаляє коротке посилання за його ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Посилання успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Посилання не знайдено")
    })
    public void deleteLink(@PathVariable long id){
        linkService.deleteById(id);

    }
    @GetMapping("/statistic/click/{id}")
    @Operation(summary = "Отримання статистики кліків", description = "Повертає кількість кліків по короткому URL за його ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статистика кліків успішно отримана",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "Посилання не знайдено", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class)))
    })
    public  int clickStatistic(@PathVariable long id){
        return  linkService.getLinkClickStatistics(id);
    }
}
