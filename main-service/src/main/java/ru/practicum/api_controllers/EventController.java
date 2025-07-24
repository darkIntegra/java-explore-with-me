package ru.practicum.api_controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import ru.practicum.entities.event.model.dto.*;
import ru.practicum.entities.event.model.enums.EventSearchOrder;
import ru.practicum.entities.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // Поиск событий с фильтрами
    @GetMapping("/admin/events")
    public ResponseEntity<List<EventDto>> getAdminEvents(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        AdminEventSearch search = AdminEventSearch.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .from(from)
                .size(size)
                .build();
        List<EventDto> events = eventService.searchAdmin(search);
        return ResponseEntity.ok(events);
    }

    // Обновление события администратором
    @PatchMapping("/admin/events/{eventId}")
    public ResponseEntity<EventDto> updateEventByAdmin(
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateAdminEventDto updateRequest) {
        EventDto updatedEvent = eventService.updateByAdmin(eventId, updateRequest);
        return ResponseEntity.ok(updatedEvent);
    }

    // Получение событий, созданных пользователем
    @GetMapping("/users/{userId}/events")
    public ResponseEntity<List<EventDto>> getEventsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        List<EventDto> events = eventService.findByUserId(userId, from, size);
        return ResponseEntity.ok(events);
    }

    // Создание нового события пользователем
    @PostMapping("/users/{userId}/events")
    @Transactional
    public ResponseEntity<EventDto> createEvent(
            @PathVariable Long userId,
            @Valid @RequestBody EventDto newEventDto) {
        EventDto createdEvent = eventService.create(userId, newEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    // Получение события по ID для пользователя
    @GetMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<EventDto> getEventByIdAndUser(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        EventDto event = eventService.findByIdAndUser(userId, eventId);
        return ResponseEntity.ok(event);
    }

    // Обновление события пользователем
    @PatchMapping("/users/{userId}/events/{eventId}")
    @Transactional
    public ResponseEntity<EventDto> updateEventByUser(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventDto eventDto) {
        EventDto updatedEvent = eventService.updateByUser(userId, eventId, eventDto);
        return ResponseEntity.ok(updatedEvent);
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

        List<EventDto> events = eventService.searchCommon(search);
        return ResponseEntity.ok(events);
    }

    // Получение события по ID
    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long eventId) {
        EventDto event = eventService.findById(eventId);
        return ResponseEntity.ok(event);
    }

}
