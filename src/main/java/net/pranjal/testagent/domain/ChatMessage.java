package net.pranjal.testagent.domain;

import java.io.Serializable;

public record ChatMessage(String timestamp, String message) implements Serializable {
}
