package com.example.footballtest.service;

import com.example.footballtest.entity.Equipe;
import com.example.footballtest.mapper.EquipeMapper;
import com.example.footballtest.mapper.JoueurMapper;
import com.example.footballtest.repository.EquipeRepository;
import com.example.generated.model.EquipeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EquipeServiceTest {

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private EquipeMapper equipeMapper;

    @Mock
    private JoueurMapper joueurMapper;

    private EquipeService equipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        equipeService = new EquipeService(equipeRepository, equipeMapper, joueurMapper);
    }

    @Test
    void testGetAllEquipes_Success() {
        // GIVEN
        Equipe equipe1 = new Equipe();
        equipe1.setName("Equipe A");
        equipe1.setAcronym("EA");
        equipe1.setBudget(2000000F);

        Equipe equipe2 = new Equipe();
        equipe2.setName("Equipe B");
        equipe2.setAcronym("EB");
        equipe2.setBudget(2000000F);

        List<Equipe> equipes = Arrays.asList(equipe1, equipe2);
        Page<Equipe> equipesPage = new PageImpl<>(equipes);

        // WHEN
        when(equipeRepository.findAll(any(Pageable.class))).thenReturn(equipesPage);
        when(equipeMapper.equipeToEquipeDTO(any(Equipe.class))).thenReturn(new EquipeDTO());

        // THEN
        Page<EquipeDTO> result = equipeService.getAllEquipes(0, 10, "name", "asc");

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(equipeRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testAddEquipe_Success() {
        // GIVEN
        EquipeDTO equipeDTO = new EquipeDTO();
        equipeDTO.setName("Equipe C");
        equipeDTO.setAcronym("EC");
        equipeDTO.setBudget(3000000F);

        Equipe equipe = new Equipe();
        equipe.setName("Equipe C");
        equipe.setAcronym("EC");
        equipe.setBudget(3000000F);

        Equipe savedEquipe = new Equipe();
        savedEquipe.setId(1L);
        savedEquipe.setName("Equipe C");

        // WHEN
        when(equipeMapper.equipeDTOToEquipe(equipeDTO)).thenReturn(equipe);
        when(equipeRepository.save(equipe)).thenReturn(savedEquipe);
        when(equipeMapper.equipeToEquipeDTO(savedEquipe)).thenReturn(equipeDTO);

        EquipeDTO result = equipeService.addEquipe(equipeDTO);

        // THEN
        assertNotNull(result);
        assertEquals("Equipe C", result.getName());
        assertEquals("EC", result.getAcronym());
        assertEquals(3000000F, result.getBudget());
    }


    @Test
    void testAddEquipe_MissingData() {
        // GIVEN
        EquipeDTO equipeDTO = new EquipeDTO();
        equipeDTO.setName("Equipe D");
        equipeDTO.setAcronym("ED");

        Equipe equipe = new Equipe();
        equipe.setName("Equipe D");
        equipe.setAcronym("ED");

        // WHEN
        when(equipeMapper.equipeDTOToEquipe(equipeDTO)).thenReturn(equipe);

        // THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            equipeService.addEquipe(equipeDTO);
        });

        assertEquals("L'équipe doit avoir un nom, un acronyme et un budget.", exception.getMessage());
    }


    @Test
    void testGetAllEquipes_InvalidSortBy() {
        // WHEN - THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            equipeService.getAllEquipes(0, 10, "invalidField", "asc");
        });

        // Verifications
        assertEquals("Le champ de tri doit être name, acronym ou budget.", exception.getMessage());
    }
}
