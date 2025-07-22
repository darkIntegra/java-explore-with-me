package ru.practicum.api_controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api_facade_services.ServiceAdmin;

import ru.practicum.entities.category.model.dto.CategoryDto;
import ru.practicum.entities.compilation.model.dto.CompilationDto;
import ru.practicum.entities.compilation.model.dto.NewCompilationDto;
import ru.practicum.entities.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.event.model.dto.AdminEventSearch;
import ru.practicum.entities.event.model.dto.UpdateAdminEventDto;
import ru.practicum.entities.user.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ControllerAdmin {

    private final ServiceAdmin serviceAdmin;

    // Создание категории
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = serviceAdmin.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    // Удаление категории
    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        serviceAdmin.deleteCategory(catId);
    }

    // Обновление категории
    @PatchMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long catId,
            @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = serviceAdmin.updateCategory(catId, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

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
        AdminEventSearch search = AdminEventSearch.builder()
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

    // Создание подборки событий
    @PostMapping("/compilations")
    @Transactional
    public ResponseEntity<CompilationDto> createCompilation(
            @Valid @RequestBody NewCompilationDto compilationDto) {
        CompilationDto createdCompilation = serviceAdmin.createCompilation(compilationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompilation);
    }

    // Удаление подборки событий
    @DeleteMapping("/compilations/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteCompilation(
            @PathVariable Long comId) {
        serviceAdmin.deleteCompilation(comId);
    }

    // Обновление подборки событий
    @PatchMapping("/compilations/{comId}")
    @Transactional
    public ResponseEntity<CompilationDto> updateCompilation(
            @PathVariable Long comId,
            @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        CompilationDto updatedCompilation = serviceAdmin.updateCompilation(comId, updateCompilationRequest);
        return ResponseEntity.ok(updatedCompilation);
    }
}