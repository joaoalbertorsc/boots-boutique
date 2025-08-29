package com.codecademy.bootsboutique;

import com.codecademy.boots.BootsApplication;
import com.codecademy.boots.entities.Boot;
import com.codecademy.boots.enums.BootType;
import com.codecademy.boots.repositories.BootRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BootsApplication.class)
@AutoConfigureMockMvc
public class BootsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BootRepository bootRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Boot mockBoot;

    @BeforeEach
    void setUp() {
        mockBoot = new Boot();
        mockBoot.setId(1);
        mockBoot.setType(BootType.CHELSEA);
        mockBoot.setSize(10.5f);
        mockBoot.setMaterial("Leather");
        mockBoot.setQuantity(5);
        mockBoot.setBestSeller(true);
    }

    @Test
    void testGetAllBoots() throws Exception {
        List<Boot> allBoots = Collections.singletonList(mockBoot);
        when(bootRepository.findAll()).thenReturn(allBoots);

        mockMvc.perform(get("/api/v1/boots"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].type").value("CHELSEA"));
    }

    @Test
    void testAddBoot() throws Exception {
        when(bootRepository.save(any(Boot.class))).thenReturn(mockBoot);

        mockMvc.perform(post("/api/v1/boots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockBoot)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDeleteBoot_Success() throws Exception {
        when(bootRepository.findById(1)).thenReturn(Optional.of(mockBoot));
        doNothing().when(bootRepository).delete(mockBoot);

        mockMvc.perform(delete("/api/v1/boots/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDeleteBoot_NotFound() throws Exception {
        when(bootRepository.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/boots/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testIncrementQuantity_Success() throws Exception {
        when(bootRepository.findById(1)).thenReturn(Optional.of(mockBoot));
        when(bootRepository.save(any(Boot.class))).thenAnswer(invocation -> {
            Boot boot = invocation.getArgument(0);
            boot.setQuantity(6);
            return boot;
        });

        mockMvc.perform(put("/api/v1/boots/1/quantity/increment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(6));
    }

    @Test
    void testDecrementQuantity_Success() throws Exception {
        when(bootRepository.findById(1)).thenReturn(Optional.of(mockBoot));
        when(bootRepository.save(any(Boot.class))).thenAnswer(invocation -> {
            Boot boot = invocation.getArgument(0);
            boot.setQuantity(4);
            return boot;
        });

        mockMvc.perform(put("/api/v1/boots/1/quantity/decrement"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(4));
    }

    @Test
    void testUpdateQuantity_NotFound() throws Exception {
        when(bootRepository.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/boots/99/quantity/increment"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetBestSellers() throws Exception {
        List<Boot> bestSellers = Collections.singletonList(mockBoot);
        when(bootRepository.findByBestSellerTrue()).thenReturn(bestSellers);

        mockMvc.perform(get("/api/v1/boots/bestsellers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bestSeller").value(true));
    }

    @Test
    void testGetBootTypes() throws Exception {
        mockMvc.perform(get("/api/v1/boots/types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value(BootType.CHELSEA.toString()));
    }

    @Test
    void testSearchBoots_ByMaterial() throws Exception {
        List<Boot> searchResults = Collections.singletonList(mockBoot);
        when(bootRepository.findByMaterial("Leather")).thenReturn(searchResults);

        mockMvc.perform(get("/api/v1/boots/search?material=Leather"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].material").value("Leather"));
    }

    @Test
    void testSearchBoots_NoParams() throws Exception {
        List<Boot> allBoots = Collections.singletonList(mockBoot);
        when(bootRepository.findAll()).thenReturn(allBoots);

        mockMvc.perform(get("/api/v1/boots/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testSearchBoots_QueryNotSupported() throws Exception {
        mockMvc.perform(get("/api/v1/boots/search?material=Leather&type=CHELSEA"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Searching by material and type simultaneously is not supported."));
    }

    @Test
    void testUpdateBoot_NotImplemented() throws Exception {
        mockMvc.perform(put("/api/v1/boots/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockBoot)))
                .andExpect(status().isNotImplemented())
                .andExpect(jsonPath("$.message").value("Updating a full boot object is not yet implemented. Please use specific update endpoints."));
    }

    @Test
    void testUpdateBootMaterial_Success() throws Exception {
        when(bootRepository.findById(1)).thenReturn(Optional.of(mockBoot));
        when(bootRepository.save(any(Boot.class))).thenAnswer(invocation -> {
            Boot boot = invocation.getArgument(0);
            boot.setMaterial("Suede");
            return boot;
        });

        mockMvc.perform(put("/api/v1/boots/1/update/material?newMaterial=Suede"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.material").value("Suede"));
    }
}
