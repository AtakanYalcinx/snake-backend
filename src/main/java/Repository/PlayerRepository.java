package Repository;


import Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    // Finde einen Spieler anhand des Benutzernamens
    Optional<Player> findByUsername(String username);

    // Optional: Überprüfe, ob ein Benutzername bereits existiert
    boolean existsByUsername(String username);
}
