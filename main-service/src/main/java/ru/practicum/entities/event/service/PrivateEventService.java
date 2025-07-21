//package ru.practicum.entities.event.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import ru.practicum.centralRepository.CategoryRepository;
//import ru.practicum.centralRepository.EventRepository;
//import ru.practicum.entities.category.model.Category;
//import ru.practicum.entities.event.model.Event;
//import ru.practicum.entities.event.model.dto.EventDto;
//import ru.practicum.entities.event.model.dto.UpdateEventDto;
//import ru.practicum.entities.event.model.enums.EventState;
//import ru.practicum.entities.event.model.enums.EventUserStateAction;
//import ru.practicum.entities.event.model.mapper.EventMapper;
//import ru.practicum.entities.user.model.User;
//import ru.practicum.entities.user.service.UserService;
//import ru.practicum.exception.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class PrivateEventService {
//
//    private final EventRepository eventRepository;
//    private final CategoryRepository categoryRepository;
//    private final UserService userService;
//
//    // Получение событий, добавленных пользователем
//    public List<EventDto> getEventsByUser(Long userId, Integer from, Integer size) {
//        return eventRepository.findAllByInitiatorId(userId, from, size)
//                .stream()
//                .map(EventMapper::toEventDto)
//                .toList();
//    }
//
//    public List<EventDto> findByUserId(Long userId, Integer from, Integer size) {
//        return eventRepository.findAllByInitiatorId(userId, from, size)
//                .stream()
//                .map(EventMapper::toEventDto)
//                .toList();
//    }
//
//    public EventDto findByIdAndUser(Long userId, Long eventId) {
//        userService.findUserById(userId);
//        Event event = findEventById(eventId);
//
//        if (!event.getInitiator().getId().equals(userId)) {
//            throw new ConditionsNotMetException("Просмотр полной информации о событии доступен только для создателя события");
//        }
//
//        return EventMapper.toEventDto(event);
//    }
//
//    public EventDto create(Long userId, EventDto newEventDto) {
//        User initiator = userService.findUserById(userId);
//        Category category = categoryRepository.findById(newEventDto.getCategory())
//                .orElseThrow(() -> new NotFoundException("Категория с id=" + newEventDto.getCategory() + " не найдена"));
//        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
//            throw new DateValidationException("Дата начала события должна быть не ранее чем через 2 часа от даты создания.");
//        }
//        Event e = EventMapper.newRequestToEvent(newEventDto, initiator, category);
//        Event e1 = eventRepository.save(e);
//        EventDto created = EventMapper.toEventDto(e1);
//        return created;
//    }
//
//    public EventDto updateByUser(Long userId, Long eventId, UpdateEventDto eventDto) {
//        userService.findUserById(userId);
//        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено"));
//        if (!event.getInitiator().getId().equals(userId)) {
//            throw new ConditionsNotMetException("Событие может редактировать только его создатель");
//        }
//
//        if (event.getState() == EventState.PUBLISHED) {
//            throw new ConditionsNotMetException("Нельзя редактировать опубликованное событие");
//        }
//
//        LocalDateTime eventDate = eventDto.getEventDate() == null ? event.getEventDate() : eventDto.getEventDate();
//        if (eventDate.isBefore(LocalDateTime.now().plusHours(1))) {
//            throw new DateValidationException("Дата начала события должна быть не ранее чем через 1 час от даты редактирования.");
//        }
//
//        if (eventDto.getCategory() != null) {
//            Category category = categoryRepository.findById(eventDto.getCategory()).orElseThrow(() -> new NotFoundException("Категория с id=" + eventDto.getCategory() + " не найдена"));
//            event.setCategory(category);
//        }
//
//        if (eventDto.getStateAction() == EventUserStateAction.SEND_TO_REVIEW) {
//            event.setState(EventState.PENDING);
//        }
//        if (eventDto.getStateAction() == EventUserStateAction.CANCEL_REVIEW) {
//            event.setState(EventState.CANCELED);
//        }
//
//        event.setAnnotation(eventDto.getAnnotation() == null ? event.getAnnotation() : eventDto.getAnnotation());
//        event.setDescription(eventDto.getDescription() == null ? event.getDescription() : eventDto.getDescription());
//        event.setEventDate(eventDate);
//        event.setPaid(eventDto.getPaid() == null ? event.getPaid() : eventDto.getPaid());
//        event.setParticipantLimit(eventDto.getParticipantLimit() == null ? event.getParticipantLimit() : eventDto.getParticipantLimit());
//        event.setRequestModeration(eventDto.getRequestModeration() == null ? event.getRequestModeration() : eventDto.getRequestModeration());
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
