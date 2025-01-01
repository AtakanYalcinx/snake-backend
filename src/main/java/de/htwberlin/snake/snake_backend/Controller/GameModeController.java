package de.htwberlin.snake.snake_backend.Controller;

import de.htwberlin.snake.snake_backend.Entity.GameMode;
import de.htwberlin.snake.snake_backend.Repository.GameModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gamemodes")
public class GameModeController {

    private final GameModeRepository gameModeRepository;

    @Autowired
    public GameModeController(GameModeRepository gameModeRepository) {
        this.gameModeRepository = gameModeRepository;
    }

    // Alle GameModes abrufen
    @GetMapping
    public ResponseEntity<List<GameMode>> getAllGameModes() {
        List<GameMode> gameModes = gameModeRepository.findAll();
        return ResponseEntity.ok(gameModes);
    }

    // Einzelnen GameMode abrufen
    @GetMapping("/{id}")
    public ResponseEntity<?> getGameModeById(@PathVariable Long id) {
        Optional<GameMode> gameMode = gameModeRepository.findById(id);
        if (gameMode.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GameMode not found");
        }
        return ResponseEntity.ok(gameMode.get());
    }

    // Neuen GameMode erstellen
    @PostMapping
    public ResponseEntity<?> createGameMode(@RequestBody GameMode gameMode) {
        System.out.println("Received GameMode: " + gameMode);
        GameMode savedGameMode = gameModeRepository.save(gameMode);
        System.out.println("Saved GameMode: " + savedGameMode);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGameMode);
    }

    // GameMode aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGameMode(@PathVariable Long id, @RequestBody GameMode updatedGameMode) {
        Optional<GameMode> existingGameMode = gameModeRepository.findById(id);
        if (existingGameMode.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GameMode not found");
        }

        GameMode gameMode = existingGameMode.get();
        gameMode.setName(updatedGameMode.getName());
        gameMode.setDescription(updatedGameMode.getDescription());
        gameMode.setSpeed(updatedGameMode.getSpeed());
        gameMode.setFruitCount(updatedGameMode.getFruitCount());
        gameMode.setFruitColor(updatedGameMode.getFruitColor());
        gameMode.setBorderActive(updatedGameMode.isBorderActive());
        gameMode.setRandomObstacles(updatedGameMode.isRandomObstacles());

        GameMode savedGameMode = gameModeRepository.save(gameMode);
        return ResponseEntity.ok(savedGameMode);
    }

    // GameMode löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGameMode(@PathVariable Long id) {
        if (!gameModeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GameMode not found");
        }
        gameModeRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
