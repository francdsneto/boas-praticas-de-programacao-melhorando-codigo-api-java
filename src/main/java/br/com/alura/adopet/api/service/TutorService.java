package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDTO;
import br.com.alura.adopet.api.dto.CadastroTutorDTO;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.tutor.iValidacaoCadastroTutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;
    @Autowired
    private List<iValidacaoCadastroTutor> validacoesCadastro;

    public void cadastrar(CadastroTutorDTO dto) {
        this.validacoesCadastro.forEach(v -> v.validar(dto));
        repository.save(new Tutor(dto));
    }

    public void atualizar(AtualizacaoTutorDTO dto) {
        Tutor tutor = this.repository.getReferenceById(dto.id());
        tutor.atualizarInformacoes(dto);
    }

}
