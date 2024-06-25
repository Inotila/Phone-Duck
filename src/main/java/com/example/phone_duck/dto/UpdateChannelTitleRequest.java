package com.example.phone_duck.dto;

public class UpdateChannelTitleRequest {
    private String newTitle;
    private Long userId;

    // Getters and Setters
    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
