//package ru.practicum.entities.event.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import ru.practicum.client.StatsClient;
//import ru.practicum.centralRepository.EventRepository;
//import ru.practicum.dto.StatsDto;
//import ru.practicum.entities.event.model.Event;
//import ru.practicum.entities.event.model.dto.EventDto;
//import ru.practicum.entities.event.model.dto.EventSearchCommon;
//import ru.practicum.entities.event.model.enums.EventState;
//import ru.practicum.entities.event.model.mapper.EventMapper;
//import ru.practicum.exception.DateValidationException;
//import ru.practicum.exception.NotFoundException;
//import ru.practicum.utils.SimpleDateTimeFormatter;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class PublicEventService {
//
//    private final EventRepository eventRepository;
//    private final StatsClient statsClient;
//
//    public List<EventDto> searchCommon(EventSearchCommon search) {
//        if (search.getRangeEnd() != null && search.getRangeStart() != null &&
//                search.getRangeEnd().isBefore(search.getRangeStart())) {
//            throw new DateValidationException("Дата начала не должна быть позже даты окончания");
//        }
//
//        List<Event> events = eventRepository.findCommonEventsByFilters(search);
//        return events.stream()
//                .map(EventMapper::toEventDto)
//                .toList();
//    }
//
//    public EventDto findById(Long eventId) {
//        Event event = findEventById(eventId);
//
//        if (event.getState() != EventState.PUBLISHED) {
//            throw new NotFoundException("Событие с id=" + eventId + " не найдено");
//        }
//
//        event.setViews(getViews(event.getId()));
//        eventRepository.save(event);
//
//        return EventMapper.toEventDto(event);
//    }
//
//    public Event findEventById(Long eventId) {
//        Event event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено"));
//        return event;
//    }
//
//    private Long getViews(Long id) {
//        List<StatsDto> result = statsClient.getStats("1900-01-01 00:00:00",
//                SimpleDateTimeFormatter.toString(LocalDateTime.now().plusMinutes(2)),
//                List.of("/events/" + id),
//                true);
//
//        return result.isEmpty() ? 0L : result.getFirst().getHits();
//    }
//}