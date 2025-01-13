package de.htwberlin.snake.snake_backend.Service;

import de.htwberlin.snake.snake_backend.Entity.GameMode;
import de.htwberlin.snake.snake_backend.Repository.GameModeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // Aktiviert die Testkonfiguration (H2 etc.)
@Transactional         // Jeder Test läuft in einer Transaktion, die danach zurückgerollt wird
class GameModeServiceTest {

    @Autowired
    private GameModeService gameModeService;

    @Autowired
    private GameModeRepository gameModeRepository;

    private GameMode gameMode;

    @BeforeEach
    void setUp() {
        // Lösche alle vorhandenen Daten, damit jeder Test in einer sauberen Umgebung startet
        gameModeRepository.deleteAll();

        // Initialisiere ein Beispiel-Objekt und speichere es in der Datenbank
        gameMode = new GameMode();
        gameMode.setName("Classic");
        gameMode.setDescription("Classic Snake Mode");
        gameMode.setSpeed(5);
        gameMode.setFruitCount(3);
        gameMode.setFruitColor("Green");
        gameMode.setBorderActive(true);
        gameMode.setRandomObstacles(false);

        gameModeRepository.save(gameMode);
    }

    @Test
    void testGetAllGameModes() {
        // When: Alle GameModes abrufen
        List<GameMode> result = gameModeService.getAllGameModes();

        // Then: Es sollte genau ein GameMode vorhanden sein
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Classic");
    }

    @Test
    void testGetGameModeById_Found() {
        // When: Den vorhandenen GameMode anhand seiner ID abrufen
        Optional<GameMode> result = gameModeService.getGameModeById(gameMode.getId());

        // Then: Das Ergebnis sollte vorhanden sein und den richtigen Namen haben
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Classic");
    }

    @Test
    void testGetGameModeById_NotFound() {
        // When: Einen GameMode mit einer nicht vorhandenen ID abrufen
        Optional<GameMode> result = gameModeService.getGameModeById(999L);

        // Then: Das Resultat sollte leer sein
        assertThat(result).isEmpty();
    }

    @Test
    void testSaveGameMode() {
        // Given: Einen neuen GameMode anlegen
        GameMode newGameMode = new GameMode();
        newGameMode.setName("NewMode");
        newGameMode.setDescription("New Mode Description");
        newGameMode.setSpeed(7);
        newGameMode.setFruitCount(4);
        newGameMode.setFruitColor("Red");
        newGameMode.setBorderActive(false);
        newGameMode.setRandomObstacles(true);

        // When: Den neuen GameMode speichern
        GameMode saved = gameModeService.saveGameMode(newGameMode);

        // Then: Das gespeicherte Objekt sollte eine ID besitzen und den korrekten Namen haben
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("NewMode");
    }

    @Test
    void testDeleteGameMode() {
        // When: Den existierenden GameMode löschen
        gameModeService.deleteGameMode(gameMode.getId());

        // Then: Der GameMode sollte in der Datenbank nicht mehr vorhanden sein
        Optional<GameMode> deleted = gameModeRepository.findById(gameMode.getId());
        assertThat(deleted).isEmpty();
    }
}
