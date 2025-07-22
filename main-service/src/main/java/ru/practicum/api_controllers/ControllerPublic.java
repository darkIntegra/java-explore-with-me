package ru.practicum.api_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api_facade_services.ServicePublic;
import ru.practicum.entities.category.model.dto.CategoryDto;
import ru.practicum.entities.compilation.model.dto.CompilationDto;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.event.model.dto.PublicEventSearch;
import ru.practicum.entities.event.model.enums.EventSearchOrder;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControllerPublic {

    private final ServicePublic servicePublic;

    // Получение подборок событий
    @GetMapping("/compilations")
    public ResponseEntity<List<CompilationDto>> getCompilations(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        List<CompilationDto> compilations = servicePublic.getCompilations(pinned, from, size);
        return ResponseEntity.ok(compilations);
    }

    // Получение подборки событий по ID
    @GetMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable Long compId) {
        CompilationDto compilation = servicePublic.getCompilationById(compId);
        return ResponseEntity.ok(compilation);
    }

    // Получение всех категорий
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        List<CategoryDto> categories = servicePublic.getAllCategories(from, size);
        return ResponseEntity.ok(categories);
    }

    // Получение категории по ID
    @GetMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long catId) {
        CategoryDto category = servicePublic.getCategoryById(catId);
        return ResponseEntity.ok(category);
    }

    // Поиск событий по фильтрам
    @GetMapping("/events")
    public ResponseEntity<List<EventDto>> searchEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "EVENT_DATE") EventSearchOrder sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {

        PublicEventSearch search = PublicEventSearch.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .sort(sort)
                .from(from)
                .size(size)
                .build();

        List<EventDto> events = servicePublic.searchEvents(search);
        return ResponseEntity.ok(events);
    }

    // Получение события по ID
    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long eventId) {
        EventDto event = servicePublic.getEventById(eventId);
        return ResponseEntity.ok(event);
    }
}