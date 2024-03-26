package com.example.jwtspringboot.controllers;

import com.example.jwtspringboot.dtos.MailStructure;
import com.example.jwtspringboot.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class SendMailController {
    private final MailService mailService;

    @PostMapping("/sendMail/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure) {
        mailService.sendMail(mail, mailStructure);
        return "Successfully";
    }
}
