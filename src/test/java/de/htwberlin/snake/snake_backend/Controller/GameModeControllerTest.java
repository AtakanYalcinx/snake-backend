package de.htwberlin.snake.snake_backend.Controller;

import de.htwberlin.snake.snake_backend.Entity.GameMode;
import de.htwberlin.snake.snake_backend.Repository.GameModeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameModeControllerTest {

    @Mock
    private GameModeRepository gameModeRepository;

    // Das Objekt, das wir testen wollen
    private GameModeController controller;

    @BeforeEach
    void setUp() {
        // Initialisiert die Mocks (@Mock) in dieser Testklasse
        MockitoAnnotations.openMocks(this);

        // Erzeugt den Controller mit dem gemockten Repository
        controller = new GameModeController(gameModeRepository);
    }

    @Test
    void testGetGameModeById_Found() {
        // Given
        GameMode gm = new GameMode();
        gm.setId(1L);
        gm.setName("Classic");

        // Wenn im Repository "findById(1L)" aufgerufen wird, soll ein Optional mit gm zurückkommen
        when(gameModeRepository.findById(1L)).thenReturn(Optional.of(gm));

        // When
        ResponseEntity<?> response = controller.getGameModeById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof GameMode);

        GameMode responseGm = (GameMode) response.getBody();
        assertEquals("Classic", responseGm.getName());

        // Verifizieren, dass das Repository wirklich nur 1x mit findById(1L) aufgerufen wurde
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

        // Verifizieren, dass das Repository wirklich nur 1x mit findById(999L) aufgerufen wurde
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

        // Wenn repository.save(...) aufgerufen wird, dann soll savedGameMode zurückkommen
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
}
