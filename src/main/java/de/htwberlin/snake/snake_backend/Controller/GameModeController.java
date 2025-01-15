package de.htwberlin.snake.snake_backend.Controller;

import de.htwberlin.snake.snake_backend.Entity.GameMode;
import de.htwberlin.snake.snake_backend.Service.GameModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://snake-frontend-mlix.onrender.com/game")
@RestController
@RequestMapping("/api/gamemodes")
public class GameModeController {

    private final GameModeService gameModeService;

    @Autowired
    public GameModeController(GameModeService gameModeService) {
        this.gameModeService = gameModeService;
    }

    // -- 1) ALLE GameModes abrufen
    @GetMapping
    public ResponseEntity<List<GameMode>> getAllGameModes() {
        List<GameMode> gameModes = gameModeService.getAllGameModes();
        return ResponseEntity.ok(gameModes);
    }

    // -- 2) EINEN GameMode abrufen (mit String-Body bei 404)
    @GetMapping("/{id}")
    public ResponseEntity<?> getGameModeById(@PathVariable Long id) {
        return gameModeService.getGameModeById(id)
        // map(...) => wir erzwingen hier ResponseEntity<?>
        .<ResponseEntity<?>>map(ResponseEntity::ok)
        // not found => 404 + "GameMode not found" im Body
        .orElseGet(() ->
        ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("GameMode not found")
        );
    }

    // -- 3) NEUEN GameMode erstellen
    @PostMapping
    public ResponseEntity<?> createGameMode(@RequestBody GameMode gameMode) {
        GameMode savedGameMode = gameModeService.saveGameMode(gameMode);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGameMode);
    }

    // -- 4) GameMode aktualisieren (mit String-Body bei 404)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGameMode(@PathVariable Long id, @RequestBody GameMode updatedGameMode) {
        return gameModeService.getGameModeById(id)
        .<ResponseEntity<?>>map(existingGameMode -> {
            existingGameMode.setName(updatedGameMode.getName());
            existingGameMode.setDescription(updatedGameMode.getDescription());
            existingGameMode.setSpeed(updatedGameMode.getSpeed());
            existingGameMode.setFruitCount(updatedGameMode.getFruitCount());
            existingGameMode.setFruitColor(updatedGameMode.getFruitColor());
            existingGameMode.setBorderActive(updatedGameMode.isBorderActive());
            existingGameMode.setRandomObstacles(updatedGameMode.isRandomObstacles());

            GameMode savedGameMode = gameModeService.saveGameMode(existingGameMode);
            return ResponseEntity.ok(savedGameMode);
        })
        .orElseGet(() ->
        ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("GameMode not found")
        );
    }

    // -- 5) GameMode löschen (mit String-Body bei 404)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGameMode(@PathVariable Long id) {
        if (gameModeService.getGameModeById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GameMode not found");
        }
        gameModeService.deleteGameMode(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
