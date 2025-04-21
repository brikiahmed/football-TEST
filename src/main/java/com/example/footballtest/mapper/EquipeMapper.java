package com.example.footballtest.mapper;

import com.example.footballtest.entity.Equipe;
import com.example.generated.model.EquipeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipeMapper {
    EquipeDTO equipeToEquipeDTO(Equipe equipe);
    Equipe equipeDTOToEquipe(EquipeDTO equipeDTO);
}
