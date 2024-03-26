package com.example.jwtspringboot.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MailStructure {
    private String subject;
    private String message;
}

