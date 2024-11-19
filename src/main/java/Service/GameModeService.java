package Service;


import Entity.GameMode;
import Repository.GameModeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


/**
 * Service-Klasse zur Verwaltung von Spielmodi.
 * Diese Klasse stellt die Geschäftslogik für CRUD-Operationen auf der
 * GameMode-Entität bereit.
 */

public class GameModeService {

    private final GameModeRepository gameModeRepository;

    @Autowired
    public GameModeService(GameModeRepository gameModeRepository) {
        this.gameModeRepository = gameModeRepository;
    }

    public List<GameMode> getAllGameModes() {
        return gameModeRepository.findAll();
    }

    public Optional<GameMode> getGameModeById(Long id) {
        return gameModeRepository.findById(id);
    }

    public GameMode saveGameMode(GameMode gameMode) {
        return gameModeRepository.save(gameMode);
    }

    public void deleteGameMode(Long id) {
        gameModeRepository.deleteById(id);
    }

}
