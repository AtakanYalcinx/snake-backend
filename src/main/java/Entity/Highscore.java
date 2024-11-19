package Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a high score entry for a player,
 * including the score, rank, and the date the score was achieved.
 */

@Entity
public class Highscore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private int score;
    private LocalDateTime rankDate;
    private int rankPosition;

    public Highscore() {
        this.rankDate = LocalDateTime.now();
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getRankDate() {
        return rankDate;
    }

    public void setRankDate(LocalDateTime rankDate) {
        this.rankDate = rankDate;
    }

    public int getRankPosition() {
        return rankPosition;
    }

    public void setRankPosition(int rankPosition) {
        this.rankPosition = rankPosition;
    }
}
