package com.substring.chat.controllers;

import com.substring.chat.entities.Message;
import com.substring.chat.entities.Room;
import com.substring.chat.playload.MessageRequest;
import com.substring.chat.repositories.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@Controller
@CrossOrigin("http://localhost:5173")
public class ChatController {

    private final RoomRepository roomRepository;

    public ChatController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Send & receive messages via WebSocket
    @MessageMapping("/sendMessage/{roomId}")   // client sends to: /app/sendMessage/{roomId}
    @SendTo("/topic/room/{roomId}")            // subscribers listen on: /topic/room/{roomId}
    public Message sendMessage(
            @DestinationVariable String roomId,
            @Payload MessageRequest request
    ) {

        // Always use roomId from URL (not from request)
        Room room = roomRepository.findByRoomId(roomId);

        if (room == null) {
            throw new RuntimeException("Room not found !!");
        }

        // Create message properly
        Message message = new Message(
                request.getContent(),
                request.getSender()
        );

        // Set timestamp
        message.setTimeStamp(LocalDateTime.now());

        // Save message
        room.getMessages().add(message);
        roomRepository.save(room);

        return message;
    }
}