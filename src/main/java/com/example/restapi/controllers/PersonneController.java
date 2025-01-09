package com.example.restapi.controllers;

import com.example.restapi.entities.Personne;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/personnes")
public class PersonneController {

    private List<Personne> personnes = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong();

    public PersonneController() {
        personnes.add(new Personne(idCounter.incrementAndGet(), "John", "Doe"));
        personnes.add(new Personne(idCounter.incrementAndGet(), "Jane", "Smith"));
    }

    @GetMapping
    public ResponseEntity<List<Personne>> getAllPersonnes() {
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable Long id) {
        Optional<Personne> personne = personnes.stream().filter(p -> p.getId().equals(id)).findFirst();
        return personne.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Personne> createPersonne(@RequestBody Personne personne) {
        personne.setId(idCounter.incrementAndGet());
        personnes.add(personne);
        return ResponseEntity.status(HttpStatus.CREATED).body(personne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@PathVariable Long id, @RequestBody Personne updatedPersonne) {
        Optional<Personne> existingPersonne = personnes.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (existingPersonne.isPresent()) {
            Personne personneToUpdate = existingPersonne.get();
            personneToUpdate.setFirstName(updatedPersonne.getFirstName());
            personneToUpdate.setLastName(updatedPersonne.getLastName());
            return ResponseEntity.ok(personneToUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        Optional<Personne> existingPersonne = personnes.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (existingPersonne.isPresent()) {
            personnes.remove(existingPersonne.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
