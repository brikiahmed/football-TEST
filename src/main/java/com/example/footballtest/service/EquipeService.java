package com.example.footballtest.service;

import com.example.footballtest.entity.Equipe;
import com.example.footballtest.entity.Joueur;
import com.example.footballtest.mapper.EquipeMapper;
import com.example.footballtest.mapper.JoueurMapper;
import com.example.footballtest.repository.EquipeRepository;
import com.example.generated.model.EquipeDTO;
import com.example.generated.model.JoueurDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final EquipeMapper equipeMapper;
    private final JoueurMapper joueurMapper;
    private static final Logger logger = LoggerFactory.getLogger(EquipeService.class);

    @Autowired
    public EquipeService(EquipeRepository equipeRepository, EquipeMapper equipeMapper, JoueurMapper joueurMapper) {
        this.equipeRepository = equipeRepository;
        this.equipeMapper = equipeMapper;
        this.joueurMapper = joueurMapper;
    }

    // Récupérer toutes les équipes avec pagination
    public Page<EquipeDTO> getAllEquipes(int page, int size, String sortBy, String sortDir) {

        // Valider les champs de tri autorisés
        List<String> allowedSortFields = List.of("name", "acronym", "budget");
        if (!allowedSortFields.contains(sortBy)) {
            logger.error("Champs de tri invalide : {}", sortBy);
            throw new IllegalArgumentException("Le champ de tri doit être name, acronym ou budget.");
        }

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Equipe> equipesPage = equipeRepository.findAll(pageable);

        return equipesPage.map(equipeMapper::equipeToEquipeDTO);
    }


    @Transactional
    public EquipeDTO addEquipe(EquipeDTO equipeDTO) {
        Equipe equipe = equipeMapper.equipeDTOToEquipe(equipeDTO);

        if (equipe.getName() == null || equipe.getAcronym() == null || equipe.getBudget() == null) {
            logger.warn("Échec d'ajout - données manquantes : name={}, acronym={}, budget={}",
                    equipe.getName(), equipe.getAcronym(), equipe.getBudget());
            throw new IllegalArgumentException("L'équipe doit avoir un nom, un acronyme et un budget.");
        }

        if (equipeDTO.getJoueurs() != null && !equipeDTO.getJoueurs().isEmpty()) {
            List<Joueur> joueurs = mapJoueursToEquipe(equipeDTO.getJoueurs(), equipe);
            equipe.setJoueurs(joueurs);
        }

        Equipe savedEquipe = equipeRepository.save(equipe);
        logger.info("Équipe ajoutée avec succès : id={}, name={}", savedEquipe.getId(), savedEquipe.getName());
        return equipeMapper.equipeToEquipeDTO(savedEquipe);
    }

    private List<Joueur> mapJoueursToEquipe(List<JoueurDTO> joueurDTOs, Equipe equipe) {
        return joueurDTOs.stream()
                .map(dto -> {
                    Joueur joueur = joueurMapper.joueurDTOToJoueur(dto);
                    joueur.setEquipe(equipe);
                    return joueur;
                })
                .toList();
    }
}
