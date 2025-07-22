package ru.practicum.api_facade_services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.practicum.entities.category.model.dto.CategoryDto;
import ru.practicum.entities.category.service.CategoryService;
import ru.practicum.entities.compilation.model.dto.CompilationDto;
import ru.practicum.entities.compilation.model.dto.NewCompilationDto;
import ru.practicum.entities.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.entities.compilation.service.CompilationService;
import ru.practicum.entities.event.model.Event;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.event.model.dto.AdminEventSearch;
import ru.practicum.entities.event.model.dto.UpdateAdminEventDto;
import ru.practicum.entities.event.service.EventService;
import ru.practicum.entities.user.model.dto.UserDto;
import ru.practicum.entities.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceAdmin {

    private final UserService userService;

    private final EventService eventService;
    private final CompilationService compilationService;
    private final CategoryService categoryService;

    @Transactional
    public CategoryDto createCategory(CategoryDto newCategoryDto) {
        return categoryService.createCategory(newCategoryDto);
    }

    @Transactional
    public void deleteCategory(Long catId) {
        categoryService.deleteCategory(catId);
    }

    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto newCategoryDto) {
        return categoryService.updateCategory(id, newCategoryDto);
    }

    @Transactional
    public List<EventDto> searchEvents(AdminEventSearch search) {
        return eventService.searchAdmin(search);
    }

    @Transactional
    public EventDto updateEventByAdmin(long eventId, UpdateAdminEventDto eventDto) {
        return eventService.updateByAdmin(eventId, eventDto);
    }

    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        return userService.getAll(ids, from, size);
    }

    public UserDto createUser(UserDto user) {
        return userService.create(user);
    }

    public void deleteUser(Long userId) {
        userService.delete(userId);
    }

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

    @Transactional
    public Event getEventById(Long eventId) {
        return eventService.findEventById(eventId);
    }
}
