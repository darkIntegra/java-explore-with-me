package ru.practicum.api_facade_services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.practicum.entities.category.model.dto.CategoryDto;
import ru.practicum.entities.category.service.CategoryService;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.event.model.dto.EventSearchAdmin;
import ru.practicum.entities.event.model.dto.UpdateAdminEventDto;
import ru.practicum.entities.event.service.EventService;
import ru.practicum.entities.user.model.User;
import ru.practicum.entities.user.model.dto.UserDto;
import ru.practicum.entities.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceAdmin {

    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;

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
    public List<EventDto> searchEvents(EventSearchAdmin search) {
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

    public User getUserById(Long userId) {
        return userService.findUserById(userId);
    }

}
