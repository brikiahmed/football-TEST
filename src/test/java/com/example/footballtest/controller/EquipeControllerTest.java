package com.example.footballtest.controller;

import com.example.footballtest.service.EquipeService;
import com.example.generated.model.EquipeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipeControllerTest {

    @Mock
    private EquipeService equipeService;

    @InjectMocks
    private EquipeController equipeController;

    @Test
    void testGetAllEquipes() {
        EquipeDTO equipe1 = new EquipeDTO().name("Paris").acronym("PSG").budget(5000000F);
        EquipeDTO equipe2 = new EquipeDTO().name("Nice").acronym("OGCN").budget(2000000F);

        Page<EquipeDTO> mockPage = new PageImpl<>(List.of(equipe1, equipe2));

        when(equipeService.getAllEquipes(0, 10, "name", "asc")).thenReturn(mockPage);

        ResponseEntity<Page<EquipeDTO>> response = equipeController.getAllEquipes(0, 10, "name", "asc");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().getContent().size());
        assertEquals("Paris", response.getBody().getContent().get(0).getName());

        verify(equipeService, times(1)).getAllEquipes(0, 10, "name", "asc");
    }

    @Test
    void testAddEquipe() {
        EquipeDTO input = new EquipeDTO().name("Marseille").acronym("OM").budget(3000000F);
        EquipeDTO saved = new EquipeDTO().name("Marseille").acronym("OM").budget(3000000F);

        when(equipeService.addEquipe(input)).thenReturn(saved);

        ResponseEntity<EquipeDTO> response = equipeController.addEquipe(input);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Marseille", response.getBody().getName());

        verify(equipeService, times(1)).addEquipe(input);
    }
}
