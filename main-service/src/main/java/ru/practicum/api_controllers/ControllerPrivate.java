package ru.practicum.api_controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api_facade_services.ServicePrivate;
import ru.practicum.entities.compilation.model.dto.CompilationDto;
import ru.practicum.entities.compilation.model.dto.NewCompilationDto;
import ru.practicum.entities.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.entities.event.model.Event;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.event.model.dto.UpdateEventDto;
import ru.practicum.entities.request.model.dto.EventRequestStatusUpdateRequest;
import ru.practicum.entities.request.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.entities.request.model.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
@Slf4j
public class ControllerPrivate {

    private final ServicePrivate servicePrivate;

    // Получение событий, созданных пользователем
    @GetMapping("/events")
    public ResponseEntity<List<EventDto>> getEventsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        List<EventDto> events = servicePrivate.getEventsByUserId(userId, from, size);
        return ResponseEntity.ok(events);
    }

    // Получение события по ID для пользователя
    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventDto> getEventByIdAndUser(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        EventDto event = servicePrivate.getEventByIdAndUser(userId, eventId);
        return ResponseEntity.ok(event);
    }

    // Создание нового события пользователем
    @PostMapping("/events")
    @Transactional
    public ResponseEntity<EventDto> createEvent(
            @PathVariable Long userId,
            @Valid @RequestBody EventDto newEventDto) {
        EventDto createdEvent = servicePrivate.createEvent(userId, newEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    // Обновление события пользователем
    @PatchMapping("/events/{eventId}")
    @Transactional
    public ResponseEntity<EventDto> updateEventByUser(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventDto eventDto) {
        EventDto updatedEvent = servicePrivate.updateEventByUser(userId, eventId, eventDto);
        return ResponseEntity.ok(updatedEvent);
    }

    // Получение всех заявок пользователя на участие в событиях
    @GetMapping("/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getAllRequestsByUser(@PathVariable Long userId) {
        List<ParticipationRequestDto> requests = servicePrivate.getAllRequestsByUser(userId);
        return ResponseEntity.ok(requests);
    }

    // Получение всех заявок на участие в событии текущего пользователя
    @GetMapping("/events/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getAllRequestsByEventAndInitiator(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        List<ParticipationRequestDto> requests = servicePrivate.getAllRequestsByEventAndInitiator(userId, eventId);
        return ResponseEntity.ok(requests);
    }

    // Создание заявки на участие в событии
    @PostMapping("/requests")
    @Transactional
    public ResponseEntity<ParticipationRequestDto> createParticipationRequest(
            @PathVariable Long userId,
            @RequestParam Long eventId) {
        ParticipationRequestDto request = servicePrivate.createParticipationRequest(userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    // Отмена заявки на участие в событии
    @PatchMapping("/requests/{requestId}/cancel")
    @Transactional
    public ResponseEntity<ParticipationRequestDto> cancelParticipationRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId) {
        ParticipationRequestDto canceledRequest = servicePrivate.cancelParticipationRequest(userId, requestId);
        return ResponseEntity.ok(canceledRequest);
    }

    // Изменение статуса заявок на участие в событии
    @PatchMapping("/events/{eventId}/requests")
    @Transactional
    public ResponseEntity<EventRequestStatusUpdateResult> updateParticipationRequestStatus(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest requestDto) {
        EventRequestStatusUpdateResult result = servicePrivate.updateParticipationRequestStatus(userId, eventId, requestDto);
        return ResponseEntity.ok(result);
    }

    // Создание подборки событий
    @PostMapping("/compilations")
    @Transactional
    public ResponseEntity<CompilationDto> createCompilation(
            @PathVariable Long userId,
            @Valid @RequestBody NewCompilationDto compilationDto) {
        CompilationDto createdCompilation = servicePrivate.createCompilation(compilationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompilation);
    }

    // Удаление подборки событий
    @DeleteMapping("/compilations/{compilationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteCompilation(
            @PathVariable Long userId,
            @PathVariable Long compilationId) {
        servicePrivate.deleteCompilation(compilationId);
    }

    // Обновление подборки событий
    @PatchMapping("/compilations/{compilationId}")
    @Transactional
    public ResponseEntity<CompilationDto> updateCompilation(
            @PathVariable Long userId,
            @PathVariable Long compilationId,
            @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        CompilationDto updatedCompilation = servicePrivate.updateCompilation(compilationId, updateCompilationRequest);
        return ResponseEntity.ok(updatedCompilation);
    }

    // Получение события по ID
    @GetMapping("/events/{eventId}/full")
    @Transactional
    public ResponseEntity<Event> getEventById(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        Event event = servicePrivate.getEventById(eventId);
        return ResponseEntity.ok(event);
    }
}