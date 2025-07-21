package ru.practicum.api_controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ru.practicum.api_facade_services.ServiceAdmin;

import ru.practicum.entities.category.model.dto.CategoryDto;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.event.model.dto.EventSearchAdmin;
import ru.practicum.entities.event.model.dto.UpdateAdminEventDto;
import ru.practicum.entities.user.model.User;
import ru.practicum.entities.user.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class ControllerAdmin {

    private final ServiceAdmin serviceAdmin;

    // Поиск событий с фильтрами (админ)
    @GetMapping("/events")
    public ResponseEntity<List<EventDto>> getAdminEvents(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        EventSearchAdmin search = EventSearchAdmin.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .from(from)
                .size(size)
                .build();
        List<EventDto> events = serviceAdmin.searchEvents(search);
        return ResponseEntity.ok(events);
    }

    // Обновление события администратором
    @PatchMapping("/events/{eventId}")
    public ResponseEntity<EventDto> updateEventByAdmin(
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateAdminEventDto updateRequest) {
        EventDto updatedEvent = serviceAdmin.updateEventByAdmin(eventId, updateRequest);
        return ResponseEntity.ok(updatedEvent);
    }

    // Получение пользователей (админ)
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        List<UserDto> users = serviceAdmin.getUsers(ids, from, size);
        return ResponseEntity.ok(users);
    }

    // Создание пользователя (админ)
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = serviceAdmin.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Удаление пользователя (админ)
    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        serviceAdmin.deleteUser(userId);
    }

    // Получение пользователя по ID (админ)
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = serviceAdmin.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // Создание категории (админ)
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = serviceAdmin.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    // Удаление категории (админ)
    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        serviceAdmin.deleteCategory(catId);
    }

    // Обновление категории (админ)
    @PatchMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long catId,
            @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = serviceAdmin.updateCategory(catId, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }
}