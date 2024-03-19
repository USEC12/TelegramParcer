package com.example.telegram.controller;

import com.example.telegram.model.Message;
import com.example.telegram.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/last/{n}")
    public ResponseEntity<List<Message>> getLastNMessages(@PathVariable int n) {
        // Используем PageRequest.of для создания объекта Pageable с 0 как номером страницы и n как размером страницы
        List<Message> messages = messageRepository.findLastNMessages(PageRequest.of(0, n));
        return ResponseEntity.ok(messages);
    }
    // Используем @RequestParam для получения даты и @DateTimeFormat для её парсинга
    @GetMapping("/date")
    public ResponseEntity<List<Message>> getMessagesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // Конвертируем LocalDate в LocalDateTime для начала и конца выбранного дня
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        // Используем метод репозитория findAllByDateBetween для получения сообщений за этот период
        List<Message> messages = messageRepository.findAllByDateBetween(startOfDay, endOfDay);

        return ResponseEntity.ok(messages);
    }
}
