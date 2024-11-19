package Service;


import Entity.Highscore;
import Repository.HighscoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service-Klasse zur Verwaltung von Highscores.
 * Diese Klasse stellt die Geschäftslogik für CRUD-Operationen auf der Highscore-Entität bereit.
 */


@Service
public class HighscoreService {

    private final HighscoreRepository highscoreRepository;

    @Autowired
    public HighscoreService(HighscoreRepository highscoreRepository) {
        this.highscoreRepository = highscoreRepository;
    }

    // Speichere einen neuen Highscore
    public Highscore saveHighscore(Highscore highscore) {
        return highscoreRepository.save(highscore);
    }

    // Finde alle Highscores eines Spielers anhand der Spieler-ID
    public List<Highscore> getHighscoresByPlayerId(Long playerId) {
        return highscoreRepository.findByPlayerId(playerId);
    }

    // Finde die Top 10 Highscores
    public List<Highscore> getTop10Highscores() {
        return highscoreRepository.findTop10ByOrderByScoreDesc();
    }

    // Lösche einen Highscore nach seiner ID
    public void deleteHighscore(Long id) {
        highscoreRepository.deleteById(id);
    }
}
