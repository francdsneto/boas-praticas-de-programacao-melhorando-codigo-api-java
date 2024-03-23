package br.com.alura.adopet.api.validacoes.adocao;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;

public interface iValidacaoSolicitacaoAdocao {
    void validar(SolicitacaoAdocaoDTO dto);
}
