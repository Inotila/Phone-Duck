package com.example.phone_duck.controller;

import com.example.phone_duck.entity.Channel;
import com.example.phone_duck.entity.Message;
import com.example.phone_duck.entity.User;
import com.example.phone_duck.service.ChannelService;
import com.example.phone_duck.service.MessageService;
import com.example.phone_duck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    // Get all channels
    @GetMapping("/")
    public List<Channel> getAllChannels() {
        return channelService.getAllChannels();
    }

    // Create a new channel
    @PostMapping("/")
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel, @RequestParam Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            channel.setUser(user.get());
            Channel newChannel = channelService.createChannel(channel);
            return ResponseEntity.status(HttpStatus.CREATED).body(newChannel);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Get a channel by id
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable Long id) {
        Optional<Channel> channel = channelService.getChannelById(id);
        return channel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a channel
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id, @RequestParam Long userId) {
        Optional<Channel> channel = channelService.getChannelById(id);
        if (channel.isPresent() && channel.get().getUser().getId().equals(userId)) {
            channelService.deleteChannel(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Update channel title
    @PatchMapping("/{id}")
    public ResponseEntity<Channel> updateChannelTitle(@PathVariable Long id, @RequestBody String newTitle, @RequestParam Long userId) {
        Optional<Channel> channel = channelService.getChannelById(id);
        if (channel.isPresent() && channel.get().getUser().getId().equals(userId)) {
            Channel updatedChannel = channelService.updateChannelTitle(id, newTitle);
            return ResponseEntity.ok(updatedChannel);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Create a message in a channel
    @PutMapping("/{id}/messages")
    public ResponseEntity<Message> createMessage(@PathVariable Long id, @RequestBody Message message, @RequestParam Long userId) {
        Optional<Channel> channel = channelService.getChannelById(id);
        Optional<User> user = userService.findById(userId);
        if (channel.isPresent() && user.isPresent()) {
            message.setUser(user.get());
            message.setChannel(channel.get());
            Message newMessage = messageService.createMessage(message);
            return ResponseEntity.status(HttpStatus.CREATED).body(newMessage);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Get all messages in a channel
    @GetMapping("/{id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByChannelId(@PathVariable Long id) {
        List<Message> messages = messageService.getAllMessagesByChannelId(id);
        return ResponseEntity.ok(messages);
    }
}
