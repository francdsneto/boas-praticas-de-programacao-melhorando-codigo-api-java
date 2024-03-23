package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDTO;
import br.com.alura.adopet.api.dto.DadosDetalhesAbrigo;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.validacoes.abrigo.iValidacaoCadastroAbrigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;
    @Autowired
    private List<iValidacaoCadastroAbrigo> validacoesCadastro;

    public List<DadosDetalhesAbrigo> listar() {
        return repository.findAll().stream().map(DadosDetalhesAbrigo::new).toList();
    }

    public void cadastrar(CadastroAbrigoDTO dto) {
        this.validacoesCadastro.forEach(v -> v.validar(dto));
        repository.save(new Abrigo(dto));
    }

}
