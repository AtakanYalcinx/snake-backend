package Repository;


import Entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    // Finde alle Spiele eines bestimmten Spielers anhand der Player ID
    List<Game> findByPlayerId(Long playerId);

    // Optional: Finde alle Spiele für einen bestimmten Modus
    List<Game> findByGameMode(String gameMode);
}
