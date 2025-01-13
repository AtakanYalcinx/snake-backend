package de.htwberlin.snake.snake_backend.Controller;

import de.htwberlin.snake.snake_backend.Entity.GameMode;
import de.htwberlin.snake.snake_backend.Repository.GameModeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test") // Aktiviert das Testprofil (auch wenn hier nur Mocks verwendet werden)
class GameModeControllerTest {

    @Mock
    private GameModeRepository gameModeRepository;

    // Das Objekt, das wir testen wollen
    private GameModeController controller;

    private GameMode gameMode;

    @BeforeEach
    void setUp() {
        // Initialisiert die Mocks (@Mock) in dieser Testklasse
        MockitoAnnotations.openMocks(this);

        // Erzeugt den Controller mit dem gemockten Repository
        controller = new GameModeController(gameModeRepository);

        // Beispiel-GameMode initialisieren
        gameMode = new GameMode();
        gameMode.setId(1L);
        gameMode.setName("Classic");
        gameMode.setDescription("Classic Snake Mode");
        gameMode.setSpeed(5);
        gameMode.setFruitCount(3);
        gameMode.setFruitColor("Green");
        gameMode.setBorderActive(true);
        gameMode.setRandomObstacles(false);
    }

    @Test
    void testGetGameModeById_Found() {
        // Given
        when(gameModeRepository.findById(1L)).thenReturn(Optional.of(gameMode));

        // When
        ResponseEntity<?> response = controller.getGameModeById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof GameMode);

        GameMode responseGm = (GameMode) response.getBody();
        assertEquals("Classic", responseGm.getName());

        verify(gameModeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetGameModeById_NotFound() {
        // Given
        when(gameModeRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = controller.getGameModeById(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("GameMode not found", response.getBody());

        verify(gameModeRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateGameMode() {
        // Given
        GameMode newGameMode = new GameMode();
        newGameMode.setName("NewMode");

        GameMode savedGameMode = new GameMode();
        savedGameMode.setId(2L);
        savedGameMode.setName("NewMode");

        when(gameModeRepository.save(any(GameMode.class))).thenReturn(savedGameMode);

        // When
        ResponseEntity<?> response = controller.createGameMode(newGameMode);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof GameMode);

        GameMode result = (GameMode) response.getBody();
        assertEquals(2L, result.getId());
        assertEquals("NewMode", result.getName());

        verify(gameModeRepository, times(1)).save(any(GameMode.class));
    }

    @Test
    void testDeleteGameMode_Found() {
        // Given
        when(gameModeRepository.existsById(1L)).thenReturn(true);

        // When
        ResponseEntity<?> response = controller.deleteGameMode(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(gameModeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteGameMode_NotFound() {
        // Given
        when(gameModeRepository.existsById(999L)).thenReturn(false);

        // When
        ResponseEntity<?> response = controller.deleteGameMode(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("GameMode not found", response.getBody());

        verify(gameModeRepository, never()).deleteById(999L);
    }

    // Neue Testfälle

    @Test
    void testGetAllGameModes() {
        // Given
        GameMode gm1 = new GameMode();
        gm1.setId(1L);
        gm1.setName("Classic");

        GameMode gm2 = new GameMode();
        gm2.setId(2L);
        gm2.setName("Speed");

        List<GameMode> gameModeList = Arrays.asList(gm1, gm2);
        when(gameModeRepository.findAll()).thenReturn(gameModeList);

        // When
        ResponseEntity<List<GameMode>> response = controller.getAllGameModes();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<GameMode> result = response.getBody();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Classic", result.get(0).getName());
        assertEquals("Speed", result.get(1).getName());

        verify(gameModeRepository, times(1)).findAll();
    }

    @Test
    void testUpdateGameMode_Found() {
        // Given: Ein vorhandener GameMode wird gefunden
        Long id = 1L;
        GameMode existingGameMode = gameMode;
        when(gameModeRepository.findById(id)).thenReturn(Optional.of(existingGameMode));

        // Wir simulieren eine Aktualisierung (z. B. neuer Name und Beschreibung)
        GameMode updateRequest = new GameMode();
        updateRequest.setName("UpdatedName");
        updateRequest.setDescription("Updated Description");
        updateRequest.setSpeed(10);
        updateRequest.setFruitCount(4);
        updateRequest.setFruitColor("Blue");
        updateRequest.setBorderActive(false);
        updateRequest.setRandomObstacles(true);

        // Wenn die Save-Methode aufgerufen wird, geben wir das aktualisierte Objekt zurück
        GameMode updatedGameMode = new GameMode();
        updatedGameMode.setId(id);
        updatedGameMode.setName(updateRequest.getName());
        updatedGameMode.setDescription(updateRequest.getDescription());
        updatedGameMode.setSpeed(updateRequest.getSpeed());
        updatedGameMode.setFruitCount(updateRequest.getFruitCount());
        updatedGameMode.setFruitColor(updateRequest.getFruitColor());
        updatedGameMode.setBorderActive(updateRequest.isBorderActive());
        updatedGameMode.setRandomObstacles(updateRequest.isRandomObstacles());

        when(gameModeRepository.save(any(GameMode.class))).thenReturn(updatedGameMode);

        // When: Den Update-Endpoint aufrufen
        ResponseEntity<?> response = controller.updateGameMode(id, updateRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof GameMode);
        GameMode result = (GameMode) response.getBody();
        assertEquals("UpdatedName", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(10, result.getSpeed());
        assertEquals(4, result.getFruitCount());
        assertEquals("Blue", result.getFruitColor());
        assertFalse(result.isBorderActive());
        assertTrue(result.isRandomObstacles());

        verify(gameModeRepository, times(1)).findById(id);
        verify(gameModeRepository, times(1)).save(any(GameMode.class));
    }

    @Test
    void testUpdateGameMode_NotFound() {
        // Given: Es wird kein GameMode gefunden
        Long id = 999L;
        when(gameModeRepository.findById(id)).thenReturn(Optional.empty());

        // Das Update-Request-Objekt
        GameMode updateRequest = new GameMode();
        updateRequest.setName("UpdatedName");

        // When: Den Update-Endpoint aufrufen
        ResponseEntity<?> response = controller.updateGameMode(id, updateRequest);

        // Then: Da kein GameMode gefunden wurde, sollte ein 404-Status zurückgegeben werden
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("GameMode not found", response.getBody());

        verify(gameModeRepository, times(1)).findById(id);
        verify(gameModeRepository, never()).save(any(GameMode.class));
    }
}
