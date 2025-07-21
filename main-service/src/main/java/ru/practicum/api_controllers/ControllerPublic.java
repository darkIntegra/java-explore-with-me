package ru.practicum.api_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api_facade_services.ServicePublic;
import ru.practicum.entities.compilation.model.dto.CompilationDto;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.event.model.dto.EventSearchCommon;

import java.util.List;

@RestController
@RequestMapping("/public")
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

    // Поиск событий по фильтрам
    @GetMapping("/events")
    public ResponseEntity<List<EventDto>> searchEvents(EventSearchCommon search) {
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