package de.htwberlin.snake.snake_backend.Repository;

import de.htwberlin.snake.snake_backend.Entity.GameMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, Long> {
}
