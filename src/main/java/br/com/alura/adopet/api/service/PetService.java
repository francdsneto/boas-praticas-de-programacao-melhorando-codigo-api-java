package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDTO;
import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;
    @Autowired
    private AbrigoRepository abrigoRepository;

    public void cadastrar(String idOuNome, CadastroPetDTO dto) {

        Abrigo abrigo = null;

        try
        {
            Long id = Long.parseLong(idOuNome);
            abrigo = abrigoRepository.getReferenceById(id);
        }
        catch (NumberFormatException nfe)
        {
            abrigo = abrigoRepository.findByNome(idOuNome);
        }

        Pet pet = new Pet(dto);
        pet.adicionarAbrigo(abrigo);

        repository.save(pet);

    }

    public List<DadosDetalhesPet> listarTodosDisponiveis() {
        List<Pet> pets = repository.findAllByAdotado(false);
        return pets.stream().map(DadosDetalhesPet::new).toList();
    }

    public List<DadosDetalhesPet> listarPetsPorAbrigo(String idOuNome) {
        List<Pet> pets = null;
        try {
            Long id = Long.parseLong(idOuNome);
            pets = repository.findAllByAbrigoId(id);
        } catch (NumberFormatException e) {
            pets = repository.findAllByAbrigoNome(idOuNome);
        }
        return pets.stream().map(DadosDetalhesPet::new).toList();
    }

}
