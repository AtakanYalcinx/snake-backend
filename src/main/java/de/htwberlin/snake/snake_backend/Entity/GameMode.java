package de.htwberlin.snake.snake_backend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "game_modes")
public class GameMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int speed;
    private int fruitCount;
    private String fruitColor;
    private boolean borderActive;
    private boolean randomObstacles;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFruitCount() {
        return fruitCount;
    }

    public void setFruitCount(int fruitCount) {
        this.fruitCount = fruitCount;
    }

    public String getFruitColor() {
        return fruitColor;
    }

    public void setFruitColor(String fruitColor) {
        this.fruitColor = fruitColor;
    }

    public boolean isBorderActive() {
        return borderActive;
    }

    public void setBorderActive(boolean borderActive) {
        this.borderActive = borderActive;
    }

    public boolean isRandomObstacles() {
        return randomObstacles;
    }

    public void setRandomObstacles(boolean randomObstacles) {
        this.randomObstacles = randomObstacles;
    }

    // Zusätzliche Funktionen für einen GameMode
    public String getGameModeDetails() {
        return String.format("Name: %s, Beschreibung: %s, Geschwindigkeit: %d, Früchte: %d, Farbe der Früchte: %s, Begrenzung: %s, Hindernisse: %s",
        name,
        description,
        speed,
        fruitCount,
        fruitColor,
        borderActive ? "Aktiviert" : "Deaktiviert",
        randomObstacles ? "Aktiviert" : "Deaktiviert"
        );
    }

    public boolean isValidGameMode() {
        return name != null && !name.isBlank()
        && speed > 0
        && fruitCount > 0
        && fruitColor != null && !fruitColor.isBlank();
    }
}
