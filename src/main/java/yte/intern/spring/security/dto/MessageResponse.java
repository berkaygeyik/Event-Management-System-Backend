package yte.intern.spring.security.dto;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageResponse {
    public final String message;
    public final MessageType messageType;
}