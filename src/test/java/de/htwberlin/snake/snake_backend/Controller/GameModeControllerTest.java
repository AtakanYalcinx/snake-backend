package de.htwberlin.snake.snake_backend.Controller;

import de.htwberlin.snake.snake_backend.Entity.GameMode;
import de.htwberlin.snake.snake_backend.Service.GameModeService;
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
@ActiveProfiles("test")
class GameModeControllerTest {

    @Mock
    private GameModeService gameModeService;

    private GameModeController controller;
    private GameMode gameMode;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Controller bekommt einen gemockten Service
        controller = new GameModeController(gameModeService);

        // Beispiel-GameMode anlegen
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
        when(gameModeService.getGameModeById(1L)).thenReturn(Optional.of(gameMode));

        ResponseEntity<?> response = controller.getGameModeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof GameMode);

        GameMode responseGm = (GameMode) response.getBody();
        assertEquals("Classic", responseGm.getName());

        verify(gameModeService, times(1)).getGameModeById(1L);
    }

    @Test
    void testGetGameModeById_NotFound() {
        when(gameModeService.getGameModeById(999L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.getGameModeById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("GameMode not found", response.getBody());

        verify(gameModeService, times(1)).getGameModeById(999L);
    }

    @Test
    void testCreateGameMode() {
        GameMode newGameMode = new GameMode();
        newGameMode.setName("NewMode");

        GameMode savedGameMode = new GameMode();
        savedGameMode.setId(2L);
        savedGameMode.setName("NewMode");

        when(gameModeService.saveGameMode(any(GameMode.class))).thenReturn(savedGameMode);

        ResponseEntity<?> response = controller.createGameMode(newGameMode);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof GameMode);

        GameMode result = (GameMode) response.getBody();
        assertEquals(2L, result.getId());
        assertEquals("NewMode", result.getName());

        verify(gameModeService, times(1)).saveGameMode(any(GameMode.class));
    }

    @Test
    void testDeleteGameMode_Found() {
        when(gameModeService.getGameModeById(1L)).thenReturn(Optional.of(gameMode));

        ResponseEntity<?> response = controller.deleteGameMode(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(gameModeService, times(1)).getGameModeById(1L);
        verify(gameModeService, times(1)).deleteGameMode(1L);
    }

    @Test
    void testDeleteGameMode_NotFound() {
        when(gameModeService.getGameModeById(999L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.deleteGameMode(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("GameMode not found", response.getBody());

        verify(gameModeService, times(1)).getGameModeById(999L);
        verify(gameModeService, never()).deleteGameMode(999L);
    }

    @Test
    void testGetAllGameModes() {
        GameMode gm1 = new GameMode();
        gm1.setId(1L);
        gm1.setName("Classic");

        GameMode gm2 = new GameMode();
        gm2.setId(2L);
        gm2.setName("Speed");

        when(gameModeService.getAllGameModes()).thenReturn(Arrays.asList(gm1, gm2));

        ResponseEntity<List<GameMode>> response = controller.getAllGameModes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<GameMode> result = response.getBody();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Classic", result.get(0).getName());
        assertEquals("Speed", result.get(1).getName());

        verify(gameModeService, times(1)).getAllGameModes();
    }

    @Test
    void testUpdateGameMode_Found() {
        Long id = 1L;
        when(gameModeService.getGameModeById(id)).thenReturn(Optional.of(gameMode));

        GameMode updateRequest = new GameMode();
        updateRequest.setName("UpdatedName");
        updateRequest.setDescription("Updated Description");
        updateRequest.setSpeed(10);
        updateRequest.setFruitCount(4);
        updateRequest.setFruitColor("Blue");
        updateRequest.setBorderActive(false);
        updateRequest.setRandomObstacles(true);

        GameMode updatedGameMode = new GameMode();
        updatedGameMode.setId(id);
        updatedGameMode.setName("UpdatedName");
        updatedGameMode.setDescription("Updated Description");
        updatedGameMode.setSpeed(10);
        updatedGameMode.setFruitCount(4);
        updatedGameMode.setFruitColor("Blue");
        updatedGameMode.setBorderActive(false);
        updatedGameMode.setRandomObstacles(true);

        when(gameModeService.saveGameMode(any(GameMode.class))).thenReturn(updatedGameMode);

        ResponseEntity<?> response = controller.updateGameMode(id, updateRequest);

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

        verify(gameModeService, times(1)).getGameModeById(id);
        verify(gameModeService, times(1)).saveGameMode(any(GameMode.class));
    }

    @Test
    void testUpdateGameMode_NotFound() {
        Long id = 999L;
        when(gameModeService.getGameModeById(id)).thenReturn(Optional.empty());

        GameMode updateRequest = new GameMode();
        updateRequest.setName("UpdatedName");

        ResponseEntity<?> response = controller.updateGameMode(id, updateRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("GameMode not found", response.getBody());

        verify(gameModeService, times(1)).getGameModeById(id);
        verify(gameModeService, never()).saveGameMode(any(GameMode.class));
    }
}
