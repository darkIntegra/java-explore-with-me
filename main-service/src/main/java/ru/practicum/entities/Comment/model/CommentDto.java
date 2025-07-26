package ru.practicum.entities.Comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.entities.event.model.dto.EventDto;
import ru.practicum.entities.user.model.dto.UserDto;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;

    UserDto user;

    EventDto event;

    String message;

    LocalDateTime created;
}