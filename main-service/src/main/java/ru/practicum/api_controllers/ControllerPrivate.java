package ru.practicum.api_controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api_facade_services.ServicePrivate;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.event.model.dto.UpdateEventDto;
import ru.practicum.entities.request.model.dto.EventRequestStatusUpdateRequest;
import ru.practicum.entities.request.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.entities.request.model.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
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

    // Создание нового события пользователем
    @PostMapping("/events")
    @Transactional
    public ResponseEntity<EventDto> createEvent(
            @PathVariable Long userId,
            @Valid @RequestBody EventDto newEventDto) {
        EventDto createdEvent = servicePrivate.createEvent(userId, newEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    // Получение события по ID для пользователя
    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventDto> getEventByIdAndUser(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        EventDto event = servicePrivate.getEventByIdAndUser(userId, eventId);
        return ResponseEntity.ok(event);
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

    // Получение всех заявок на участие в событии текущего пользователя
    @GetMapping("/events/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getAllRequestsByEventAndInitiator(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        List<ParticipationRequestDto> requests = servicePrivate.getAllRequestsByEventAndInitiator(userId, eventId);
        return ResponseEntity.ok(requests);
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

    // Получение всех заявок пользователя на участие в событиях
    @GetMapping("/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getAllRequestsByUser(@PathVariable Long userId) {
        List<ParticipationRequestDto> requests = servicePrivate.getAllRequestsByUser(userId);
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
}