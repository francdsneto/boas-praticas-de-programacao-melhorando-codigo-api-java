package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;

public interface iValidacaoSolicitacaoAdocao {
    void validar(SolicitacaoAdocaoDTO dto);
}
