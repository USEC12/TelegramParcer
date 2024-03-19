package com.example.telegram.repository;

import com.example.telegram.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;



import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {



    @Query("SELECT m FROM Message m WHERE m.date BETWEEN :start AND :end")
    List<Message> findAllByDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT m FROM Message m ORDER BY m.date DESC")
    List<Message> findLastNMessages(Pageable pageable);
}