package Repository;


import Entity.Highscore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HighscoreRepository extends JpaRepository<Highscore, Long> {

    // Finde alle Highscores eines Spielers anhand der Spieler-ID
    List<Highscore> findByPlayerId(Long playerId);

    // Finde die Top 10 Highscores, sortiert nach Score absteigend
    List<Highscore> findTop10ByOrderByScoreDesc();
}
