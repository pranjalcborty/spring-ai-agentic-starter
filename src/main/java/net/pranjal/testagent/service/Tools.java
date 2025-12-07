package net.pranjal.testagent.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.Date;

public class Tools {

    @Tool(name = "current-date-time", description = "Returns the current time and date when user asks for it.")
    public String now() {
        System.out.println(new Date());
        return new Date().toString();
    }
}
