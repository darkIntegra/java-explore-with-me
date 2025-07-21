package ru.practicum.api_facade_services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.entities.compilation.model.dto.CompilationDto;
import ru.practicum.entities.compilation.model.dto.NewCompilationDto;
import ru.practicum.entities.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.entities.compilation.service.CompilationService;
import ru.practicum.entities.event.model.Event;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.event.model.dto.UpdateEventDto;
import ru.practicum.entities.event.service.EventService;
import ru.practicum.entities.request.model.dto.EventRequestStatusUpdateRequest;
import ru.practicum.entities.request.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.entities.request.model.dto.ParticipationRequestDto;
import ru.practicum.entities.request.service.ParticipationRequestService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicePrivate {

    private final EventService eventService;
    private final CompilationService compilationService;
    private final ParticipationRequestService participationRequestService;

    @Transactional
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        return compilationService.createCompilation(compilationDto);
    }

    @Transactional
    public void deleteCompilation(Long compilationId) {
        compilationService.deleteCompilation(compilationId);
    }

    @Transactional
    public CompilationDto updateCompilation(Long compilationId, UpdateCompilationRequest updateCompilationRequest) {
        return compilationService.updateCompilation(compilationId, updateCompilationRequest);
    }

    public List<EventDto> getEventsByUserId(Long userId, Integer from, Integer size) {
        return eventService.findByUserId(userId, from, size);
    }

    public EventDto getEventByIdAndUser(Long userId, Long eventId) {
        return eventService.findByIdAndUser(userId, eventId);
    }

    @Transactional
    public Event getEventById(Long eventId) {
        return eventService.findEventById(eventId);
    }

    @Transactional
    public EventDto createEvent(Long userId, EventDto newEventDto) {
        return eventService.create(userId, newEventDto);
    }

    @Transactional
    public EventDto updateEventByUser(Long userId, Long eventId, UpdateEventDto eventDto) {
        return eventService.updateByUser(userId, eventId, eventDto);
    }

    public List<ParticipationRequestDto> getAllRequestsByUser(Long userId) {
        return participationRequestService.getAllByUser(userId);
    }

    public List<ParticipationRequestDto> getAllRequestsByEventAndInitiator(Long userId, Long eventId) {
        return participationRequestService.getAllByEventAndInitiator(userId, eventId);
    }

    @Transactional
    public ParticipationRequestDto createParticipationRequest(Long userId, Long eventId) {
        return participationRequestService.create(userId, eventId);
    }

    @Transactional
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        return participationRequestService.cancel(userId, requestId);
    }

    @Transactional
    public EventRequestStatusUpdateResult updateParticipationRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest requestDto) {
        return participationRequestService.updateStatus(userId, eventId, requestDto);
    }
}