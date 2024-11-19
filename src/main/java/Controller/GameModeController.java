package Controller;

import Entity.GameMode;
import Service.GameModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/gamemodes")


public class GameModeController {

    @Autowired
    private GameModeService gameModeService;

    // GET: Alle Spielmodi abrufen
    @GetMapping
    public ResponseEntity<List<GameMode>> getAllGameModes() {
        List<GameMode> gameModes = gameModeService.getAllGameModes();
        return ResponseEntity.ok(gameModes);
    }

    // GET: Spielmodus nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<GameMode> getGameModeById(@PathVariable Long id) {
        Optional<GameMode> gameMode = gameModeService.getGameModeById(id);
        return gameMode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Neuen Spielmodus erstellen
    @PostMapping
    public ResponseEntity<GameMode> createGameMode(@RequestBody GameMode gameMode) {
        GameMode createdGameMode = gameModeService.saveGameMode(gameMode);
        return ResponseEntity.ok(createdGameMode);
    }

    // PUT: Spielmodus aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<GameMode> updateGameMode(@PathVariable Long id, @RequestBody GameMode gameModeDetails) {
        Optional<GameMode> existingGameMode = gameModeService.getGameModeById(id);
        if (existingGameMode.isPresent()) {
            GameMode gameMode = existingGameMode.get();
            gameMode.setName(gameModeDetails.getName());
            gameMode.setDescription(gameModeDetails.getDescription());
            gameMode.setSpeed(gameModeDetails.getSpeed());
            gameMode.setFruitCount(gameModeDetails.getFruitCount());
            GameMode updatedGameMode = gameModeService.saveGameMode(gameMode);
            return ResponseEntity.ok(updatedGameMode);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Spielmodus löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameMode(@PathVariable Long id) {
        gameModeService.deleteGameMode(id);
        return ResponseEntity.noContent().build();
    }

}
