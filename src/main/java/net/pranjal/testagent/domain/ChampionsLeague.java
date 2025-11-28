package net.pranjal.testagent.domain;

import java.util.List;

public record ChampionsLeague(List<Trophy> tropiesList) {
}

record Trophy(String year, String finalVenue, String finalOpponent) {
}
