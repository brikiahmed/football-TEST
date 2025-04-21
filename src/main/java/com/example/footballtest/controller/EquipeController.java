package com.example.footballtest.controller;

import com.example.footballtest.service.EquipeService;
import com.example.generated.model.EquipeDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    private static final Logger logger = LoggerFactory.getLogger(EquipeService.class);
    private final EquipeService equipeService;

    @Autowired
    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @Operation(summary = "Récupérer la liste paginée des équipes avec joueurs")
    @GetMapping("/getAll")
    public ResponseEntity<Page<EquipeDTO>> getAllEquipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        logger.info("GET /equipes-nice/getAll - page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy, sortDir);

        Page<EquipeDTO> result = equipeService.getAllEquipes(page, size, sortBy, sortDir);

        logger.info("Résultat : {} équipes retournées sur {} pages", result.getNumberOfElements(), result.getTotalPages());

        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Ajouter une équipe")
    @PostMapping
    public ResponseEntity<EquipeDTO> addEquipe(@RequestBody EquipeDTO equipeDTO) {
        EquipeDTO createdEquipeDTO = equipeService.addEquipe(equipeDTO);

        logger.info("Équipe ajoutée avec succès : nom={}", createdEquipeDTO.getName());

        return ResponseEntity.ok(createdEquipeDTO);
    }
}
