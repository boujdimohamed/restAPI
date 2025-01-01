package com.example.rest.api.repository;

import com.example.rest.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository für Task-Entitäten.
 * - Bietet grundlegende CRUD-Operationen durch JpaRepository.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Findet eine Aufgabe basierend auf ihrer ID.
     *
     * @param id ID der Aufgabe
     * @return Optionale Aufgabe (wird leer sein, falls nicht gefunden)
     */
    @SuppressWarnings("null")
    Optional<Task> findById(Long id);

    /**
     * Speichert eine neue Aufgabe oder aktualisiert eine bestehende.
     *
     * @param task Die zu speichernde Aufgabe
     * @return Die gespeicherte Aufgabe
     */
    @SuppressWarnings({ "null", "unchecked" })
    @Override
    Task save(Task task);

    /**
     * Löscht eine Aufgabe basierend auf ihrer ID.
     *
     * @param id ID der Aufgabe
     */
    @Override
    void deleteById(@SuppressWarnings("null") Long id);
}
