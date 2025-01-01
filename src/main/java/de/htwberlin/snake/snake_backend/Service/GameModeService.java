package de.htwberlin.snake.snake_backend.Service;

import de.htwberlin.snake.snake_backend.Entity.GameMode;
import de.htwberlin.snake.snake_backend.Repository.GameModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
