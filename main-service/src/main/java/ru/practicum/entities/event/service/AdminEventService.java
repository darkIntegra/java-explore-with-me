//package ru.practicum.entities.event.service;
//
//import lombok.RequiredArgsConstructor;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import ru.practicum.centralRepository.CategoryRepository;
//import ru.practicum.centralRepository.EventRepository;
//import ru.practicum.entities.category.model.Category;
//import ru.practicum.entities.event.model.Event;
//import ru.practicum.entities.event.model.dto.EventDto;
//import ru.practicum.entities.event.model.dto.EventSearchAdmin;
//import ru.practicum.entities.event.model.dto.UpdateAdminEventDto;
//import ru.practicum.entities.event.model.enums.EventAdminStateAction;
//import ru.practicum.entities.event.model.enums.EventState;
//import ru.practicum.entities.event.model.mapper.EventMapper;
//import ru.practicum.exception.ConditionsNotMetException;
//import ru.practicum.exception.DateValidationException;
//import ru.practicum.exception.NotFoundException;
//
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class AdminEventService {
//
//    private final EventRepository eventRepository;
//    private final CategoryRepository categoryRepository;
//
//    public List<EventDto> getAdminEvents(EventSearchAdmin search) {
//        List<Event> events = eventRepository.findAdminEventsByFilters(search);
//        return events.stream()
//                .map(EventMapper::toEventDto)
//                .toList();
//    }
//
//    @Transactional
//    public EventDto updateEvent(long eventId, UpdateAdminEventDto eventDto) {
//        Event event = findEventById(eventId);
//        LocalDateTime eventDate = eventDto.getEventDate() == null ? event.getEventDate() : eventDto.getEventDate();
//        if (eventDate.isBefore(LocalDateTime.now().plusHours(1))) {
//            throw new DateValidationException("Дата начала события должна быть не ранее чем через 1 час от даты редактирования.");
//        }
//        if (event.getState() == EventState.PUBLISHED && eventDto.getStateAction() == EventAdminStateAction.REJECT_EVENT) {
//            throw new ConditionsNotMetException("Опубликованное событие нельзя отклонить.");
//        }
//        if (event.getState() != EventState.PENDING && eventDto.getStateAction() == EventAdminStateAction.PUBLISH_EVENT) {
//            throw new ConditionsNotMetException("Опубликовать можно только событие в состоянии ожидания.");
//        }
//        if (eventDto.getCategory() != null) {
//            Category category = categoryRepository.findById(eventDto.getCategory()).orElseThrow(() -> new NotFoundException("Категория с id=" + eventDto.getCategory() + " не найдена"));
//            event.setCategory(category);
//        }
//
//        event.setAnnotation(eventDto.getAnnotation() == null ? event.getAnnotation() : eventDto.getAnnotation());
//        event.setDescription(eventDto.getDescription() == null ? event.getDescription() : eventDto.getDescription());
//        event.setEventDate(eventDate);
//        event.setPaid(eventDto.getPaid() == null ? event.getPaid() : eventDto.getPaid());
//        event.setParticipantLimit(eventDto.getParticipantLimit() == null ? event.getParticipantLimit() : eventDto.getParticipantLimit());
//        event.setRequestModeration(eventDto.getRequestModeration() == null ? event.getRequestModeration() : eventDto.getRequestModeration());
//        event.setState(eventDto.getStateAction() == null ? event.getState() :
//                eventDto.getStateAction() == EventAdminStateAction.PUBLISH_EVENT ? EventState.PUBLISHED : EventState.CANCELED);
//        event.setTitle(eventDto.getTitle() == null ? event.getTitle() : eventDto.getTitle());
//        event.setLat(eventDto.getLocation() == null ? event.getLat() : eventDto.getLocation().getLat());
//        event.setLon(eventDto.getLocation() == null ? event.getLon() : eventDto.getLocation().getLon());
//
//        return EventMapper.toEventDto(eventRepository.save(event));
//    }
//
//    @Transactional
//    public Event findEventById(Long eventId) {
//        Event event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено"));
//        return event;
//    }
//}