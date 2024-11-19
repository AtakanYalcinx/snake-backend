package Service;


import Entity.Game;
import Repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Service-Klasse zur Verwaltung von Spielen.
 * Diese Klasse stellt die Geschäftslogik für CRUD-Operationen auf der Game-Entität bereit.
 */

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // Speichere ein neues Spiel
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    // Finde ein Spiel nach seiner ID
    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    // Finde alle Spiele eines bestimmten Spielers anhand der Spieler-ID
    public List<Game> getGamesByPlayerId(Long playerId) {
        return gameRepository.findByPlayerId(playerId);
    }

    // Lösche ein Spiel nach seiner ID
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
