package Controller;

import Entity.Highscore;
import Service.HighscoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/highscores")

public class HighscoreController {

    @Autowired
    private HighscoreService highscoreService;

    // GET: Top 10 Highscores abrufen
    @GetMapping("/top10")
    public ResponseEntity<List<Highscore>> getTop10Highscores() {
        List<Highscore> top10 = highscoreService.getTop10Highscores();
        return ResponseEntity.ok(top10);
    }

}
