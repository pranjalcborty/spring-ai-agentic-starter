package net.pranjal.chat.domain;

import java.util.List;

public record ChampionsLeague(List<Trophy> trophies) {
}

record Trophy(String year, String finalVenue, String finalOpponent) {
}
