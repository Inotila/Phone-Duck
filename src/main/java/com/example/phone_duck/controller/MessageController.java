package com.example.phone_duck.controller;

import com.example.phone_duck.entity.Message;
import com.example.phone_duck.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Get a message by id
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update message content
    @PatchMapping("/{id}")
    public ResponseEntity<Message> updateMessageContent(@PathVariable Long id, @RequestBody String newContent, @RequestParam Long userId) {
        Optional<Message> message = messageService.getMessageById(id);
        if (message.isPresent() && message.get().getUser().getId().equals(userId)) {
            Message updatedMessage = messageService.updateMessageContent(id, newContent);
            return ResponseEntity.ok(updatedMessage);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Delete a message
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id, @RequestParam Long userId) {
        Optional<Message> message = messageService.getMessageById(id);
        if (message.isPresent() && message.get().getUser().getId().equals(userId)) {
            messageService.deleteMessage(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
