package com.example.footballtest.repository;

import com.example.footballtest.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    // Recherche d'une équipe par son acronyme
    Optional<Equipe> findByAcronym(String acronym);
}
