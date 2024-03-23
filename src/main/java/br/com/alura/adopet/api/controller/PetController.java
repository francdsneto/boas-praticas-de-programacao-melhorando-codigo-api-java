package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroPetDTO;
import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService service;

    @PostMapping("/noabrigo/{idOuNome}")
    @Transactional
    public ResponseEntity<String> cadastrar(@PathVariable String idOuNome, @RequestBody @Valid CadastroPetDTO dto) {
        try
        {
            service.cadastrar(idOuNome,dto);
            return ResponseEntity.ok().build();
        }
        catch(EntityNotFoundException ex)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhesPet>> listarTodosDisponiveis() {
        return ResponseEntity.ok(service.listarTodosDisponiveis());
    }

    @GetMapping("/porabrigo/{idOuNome}")
    public ResponseEntity<List<DadosDetalhesPet>> listarPetsPorAbrigo(@PathVariable String idOuNome) {
        return ResponseEntity.ok(service.listarPetsPorAbrigo(idOuNome));
    }

}
