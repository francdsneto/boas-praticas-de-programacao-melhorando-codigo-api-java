package br.com.alura.adopet.api.validacoes.tutor;

import br.com.alura.adopet.api.dto.CadastroTutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTelefoneJaCadastrado implements iValidacaoCadastroTutor {

    @Autowired
    private TutorRepository repository;

    @Override
    public void validar(CadastroTutorDTO dto) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(dto.telefone());
        if (telefoneJaCadastrado)
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");

    }
}
