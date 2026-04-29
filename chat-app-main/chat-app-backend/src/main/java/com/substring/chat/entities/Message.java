package com.substring.chat.entities;

import java.time.LocalDateTime;

public class Message {

    private String content;
    private String sender;
    private LocalDateTime timeStamp;

    // Constructor matching controller usage
    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
        this.timeStamp = LocalDateTime.now();
    }

    // Getters
    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    // Setter for timestamp (used in controller)
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}