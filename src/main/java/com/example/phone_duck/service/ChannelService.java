package com.example.phone_duck.service;

import com.example.phone_duck.entity.Channel;
import com.example.phone_duck.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    public Channel createChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    public Optional<Channel> getChannelById(Long id) {
        return channelRepository.findById(id);
    }

    public void deleteChannel(Long id) {
        channelRepository.deleteById(id);
    }

    public Channel updateChannelTitle(Long id, String newTitle) {
        Optional<Channel> optionalChannel = channelRepository.findById(id);
        if (optionalChannel.isPresent()) {
            Channel channel = optionalChannel.get();
            channel.setTitle(newTitle);
            return channelRepository.save(channel);
        }
        return null;
    }
}
