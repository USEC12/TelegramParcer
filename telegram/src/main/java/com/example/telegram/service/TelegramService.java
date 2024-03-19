package com.example.telegram.service;

import com.example.telegram.model.Message;
import com.example.telegram.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
//Получение сообщений в реальном времени
@Service
public class TelegramService {
    private final MessageRepository messageRepository;
    private final TelegramBot bot;

    @Autowired
    public TelegramService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.bot = new TelegramBot("6514963333:AAGeO8ZcxCsalQFgwf3v3CPYA8FPe_BR34k");
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void processUpdate(Update update) {
        // Логика обработки сообщения и сохранения в БД
        if (update.message() != null && update.message().text() != null) {
            Message message = new Message();
            message.setContent(update.message().text());
            message.setDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(update.message().date()), ZoneId.systemDefault()));
            message.setAuthor(update.message().from().firstName());

            messageRepository.save(message);
        }

    }
}