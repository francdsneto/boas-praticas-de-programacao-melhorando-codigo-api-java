package br.com.alura.adopet.api.validacoes.abrigo;

import br.com.alura.adopet.api.dto.CadastroAbrigoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoNomeJaCadastrado implements iValidacaoCadastroAbrigo {

    @Autowired
    private AbrigoRepository repository;

    @Override
    public void validar(CadastroAbrigoDTO dto) {
        var nomeJaCadastrado = repository.existsByNome(dto.nome());
        if(nomeJaCadastrado)
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
    }
}
