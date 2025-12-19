package net.pranjal.chat.service;

import org.springframework.ai.tool.annotation.Tool;

import java.util.Date;

public class Tools {

    @Tool(name = "current-date-time", description = "Returns the current time and date when user asks for it.")
    public String now() {
        System.out.println(new Date());
        return new Date().toString();
    }
}
