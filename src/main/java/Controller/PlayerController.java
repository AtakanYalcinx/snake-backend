package com.example.snakegame.controller;

import Entity.Player;
import Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Registrierung eines neuen Spielers
    @PostMapping("/register")
    public Player registerPlayer(@RequestBody Player player) {
        return playerService.savePlayer(player);
    }

    // Holen eines bestimmten Spielers per ID
    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    // Optional: Holen eines Spielers per Benutzername
    @GetMapping("/username/{username}")
    public Player getPlayerByUsername(@PathVariable String username) {
        return playerService.findByUsername(username).orElseThrow(() -> new RuntimeException("Player not found"));
    }
}
