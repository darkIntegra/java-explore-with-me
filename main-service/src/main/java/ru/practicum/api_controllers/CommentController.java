package ru.practicum.api_controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.practicum.entities.Comment.model.CommentDto;
import ru.practicum.entities.Comment.model.CommentUpdateDto;
import ru.practicum.entities.Comment.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    // Admin API
    // 1.1 Получение всех комментариев
    @GetMapping("/admin/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsForAdmin(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        List<CommentDto> comments = commentService.getComments(rangeStart, rangeEnd, from, size);
        return ResponseEntity.ok(comments);
    }

    // 1.2 Удаление комментария администратором
    @DeleteMapping("/admin/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentByAdmin(@PathVariable Long commentId) {
        commentService.deleteAdminComment(commentId);
        return ResponseEntity.noContent().build();
    }

    // Public API
    // 2.1 Получение комментариев по событию
    @GetMapping("/comments/{eventId}")
    public ResponseEntity<List<CommentDto>> getCommentsByEventId(
            @PathVariable Long eventId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        List<CommentDto> comments = commentService.getCommentsByEventId(rangeStart, rangeEnd, eventId, from, size);
        return ResponseEntity.ok(comments);
    }

    // Private API
    // 3.1 Получение комментариев пользователя
    @GetMapping("/users/comments/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsForUser(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        List<CommentDto> comments = commentService.getCommentsByUserId(rangeStart, rangeEnd, userId, from, size);
        return ResponseEntity.ok(comments);
    }

    // 3.2 Добавление нового комментария
    @PostMapping("/users/comments/{userId}/{eventId}")
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CommentDto commentNewDto,
            @PathVariable Long userId,
            @PathVariable Long eventId) {

        CommentDto createdComment = commentService.addComment(userId, eventId, commentNewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    // 3.3 Обновление комментария
    @PatchMapping("/users/comments/{userId}/{commentId}")
    public ResponseEntity<CommentDto> updateCommentByUser(
            @Valid @RequestBody CommentUpdateDto commentUpdateDto,
            @PathVariable Long userId,
            @PathVariable Long commentId) {

        CommentDto updatedComment = commentService.updateComment(userId, commentId, commentUpdateDto);
        return ResponseEntity.ok(updatedComment);
    }

    // 3.4 Удаление комментария пользователем
    @DeleteMapping("/users/comments/{userId}/{commentId}")
    public ResponseEntity<Void> deleteCommentByUser(
            @PathVariable Long userId,
            @PathVariable Long commentId) {

        commentService.deletePrivateComment(userId, commentId);
        return ResponseEntity.noContent().build();
    }
}