package com.example.footballtest.mapper;

import com.example.footballtest.entity.Joueur;
import com.example.generated.model.JoueurDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JoueurMapper {
    JoueurDTO joueurToJoueurDTO(Joueur joueur);
    Joueur joueurDTOToJoueur(JoueurDTO joueurDTO);
}

