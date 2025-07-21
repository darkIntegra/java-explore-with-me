package ru.practicum.api_facade_services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.practicum.entities.compilation.model.dto.CompilationDto;
import ru.practicum.entities.compilation.service.CompilationService;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.event.model.dto.EventSearchCommon;
import ru.practicum.entities.event.service.EventService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicePublic {

    private final CompilationService compilationService;
    private final EventService eventService;

    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        return compilationService.getAllCompilations(pinned, from, size);
    }

    public CompilationDto getCompilationById(Long compId) {
        return compilationService.getCompilationById(compId);
    }

    public List<EventDto> searchEvents(EventSearchCommon search) {
        return eventService.searchCommon(search);
    }

    public EventDto getEventById(Long eventId) {
        return eventService.findById(eventId);
    }
}