package Controller;

import Entity.Player;
import Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/login")
    public ResponseEntity<Player> login(@RequestBody Player loginRequest) {
        Optional<Player> player = playerRepository.findByUsername(loginRequest.getUsername());

        if (player.isPresent() && BCrypt.checkpw(loginRequest.getPasswordHash(), player.get().getPasswordHash())) {
            // Authentifizierung erfolgreich
            return ResponseEntity.ok(player.get());
        } else {
            // Authentifizierung fehlgeschlagen
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
