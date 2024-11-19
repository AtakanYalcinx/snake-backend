package Controller;


import Entity.Game;
import Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Handles API requests related to games.
 * Provides endpoints to create, retrieve, update, and delete games.
 */

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    // GET: Alle Spiele abrufen
    @GetMapping("/")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getGamesByPlayerId(1L);  // Beispiel für eine bestimmte Player-ID
        return ResponseEntity.ok(games);
    }

    // GET: Spiel nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        Optional<Game> game = gameService.getGameById(id);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Neues Spiel erstellen
    @PostMapping("/")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game createdGame = gameService.saveGame(game);
        return ResponseEntity.ok(createdGame);
    }

    // PUT: Bestehendes Spiel aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game gameDetails) {
        Game updatedGame = gameService.saveGame(gameDetails);  // Annahme: saveGame() aktualisiert das Spiel, wenn es bereits existiert
        if (updatedGame != null) {
            return ResponseEntity.ok(updatedGame);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Spiel löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
