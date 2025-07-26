package ru.practicum.centralRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.entities.Comment.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM comments AS c " +
            "WHERE ((CAST(:rangeStart as DATE) IS NULL OR c.created >= :rangeStart) " +
            "AND (CAST(:rangeEnd as DATE) IS NULL OR c.created <= :rangeEnd)) " +
            "GROUP BY c.id " +
            "ORDER BY c.id ASC")
    List<Comment> findFilteredComments(
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable);

    @Query("SELECT c FROM comments AS c " +
            "WHERE :eventId = c.event.id " +
            "AND ((CAST(:rangeStart as DATE) IS NULL OR c.created >= :rangeStart) " +
            "AND (CAST(:rangeEnd as DATE) IS NULL OR c.created <= :rangeEnd)) " +
            "GROUP BY c.id " +
            "ORDER BY c.id ASC")
    List<Comment> findCommentsByEventIdAndDates(
            @Param("eventId") Long eventId,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable);

    @Query("SELECT c FROM comments AS c " +
            "WHERE :userId = c.user.id " +
            "AND ((CAST(:rangeStart as DATE) IS NULL OR c.created >= :rangeStart) " +
            "AND (CAST(:rangeEnd as DATE) IS NULL OR c.created <= :rangeEnd)) " +
            "ORDER BY c.id ASC")
    List<Comment> findCommentsByUserIdAndDates(
            @Param("userId") Long userId,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable);
}