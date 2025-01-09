package com.example.restapi.controllers;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.entities.Voiture;

@RestController
@RequestMapping("/api/charges")
public class VoitureController {

    @GetMapping
    public ResponseEntity<List<Voiture>> getAllBatterie() {
        String bdd_host = System.getenv("MYSQL_HOST");
        String jdbcUrl = "jdbc:mysql://bdd:3306/Tesla?autoReconnect=true&useSSL=false";
        String username = "root";
        String password = "root";

        String selectQuery = "SELECT batterie, heure, temperature FROM teslainfo";
        List<Voiture> voitures = new ArrayList<>();

        try (java.sql.Connection dbConnection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = dbConnection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Parcourir les résultats et construire les objets Voiture
            while (resultSet.next()) {
                float batterie = resultSet.getFloat("batterie");
                String heure = resultSet.getString("heure");
                float temperature = resultSet.getFloat("temperature");

                // Ajouter un objet Voiture à la liste
                voitures.add(new Voiture(batterie, heure, temperature));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des données : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

        // Retourner les données sous forme de réponse JSON
        return ResponseEntity.ok(voitures);
    }
}
