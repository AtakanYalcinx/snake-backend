package Service;

import Entity.Player;
import Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // Speichere einen neuen Spieler
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    // Finde einen Spieler nach seiner ID
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    // Finde einen Spieler nach seinem Benutzernamen
    public Optional<Player> findByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    // Lösche einen Spieler nach seiner ID
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

}
