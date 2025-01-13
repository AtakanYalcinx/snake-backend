package de.htwberlin.snake.snake_backend.Service;

import de.htwberlin.snake.snake_backend.Entity.GameMode;
import de.htwberlin.snake.snake_backend.Repository.GameModeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test") // Aktiviere das Profil "test" für die H2-Datenbank
@ExtendWith(MockitoExtension.class)
class GameModeServiceTest {

    @Mock
    private GameModeRepository gameModeRepository;

    @InjectMocks
    private GameModeService gameModeService;

    private GameMode gameMode;

    @BeforeEach
    void setUp() {
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
    void testGetAllGameModes() {
        // Given
        given(gameModeRepository.findAll()).willReturn(Arrays.asList(gameMode));

        // When
        List<GameMode> result = gameModeService.getAllGameModes();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Classic");
        verify(gameModeRepository, times(1)).findAll();
    }

    @Test
    void testGetGameModeById_Found() {
        // Given
        given(gameModeRepository.findById(1L)).willReturn(Optional.of(gameMode));

        // When
        Optional<GameMode> result = gameModeService.getGameModeById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Classic");
        verify(gameModeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetGameModeById_NotFound() {
        // Given
        given(gameModeRepository.findById(999L)).willReturn(Optional.empty());

        // When
        Optional<GameMode> result = gameModeService.getGameModeById(999L);

        // Then
        assertThat(result).isEmpty();
        verify(gameModeRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveGameMode() {
        // Given
        given(gameModeRepository.save(gameMode)).willReturn(gameMode);

        // When
        GameMode saved = gameModeService.saveGameMode(gameMode);

        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("Classic");
        verify(gameModeRepository, times(1)).save(gameMode);
    }

    @Test
    void testDeleteGameMode() {
        // When
        gameModeService.deleteGameMode(1L);

        // Then
        verify(gameModeRepository, times(1)).deleteById(1L);
    }
}
