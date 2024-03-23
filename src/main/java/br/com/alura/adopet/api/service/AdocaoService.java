package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.adocao.iValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoService {

    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private List<iValidacaoSolicitacaoAdocao> validadoresAdocaoPets;


    public void solicitar(SolicitacaoAdocaoDTO dto){

        var pet = petRepository.getReferenceById(dto.idPet());
        var tutor = tutorRepository.getReferenceById(dto.idTutor());

        //TODO chamar as validações
        this.validadoresAdocaoPets.forEach(v -> v.validar(dto));

        Adocao adocao = new Adocao(tutor,pet,dto.motivo());
        repository.save(adocao);

        var mensagem = "Olá " +adocao.getPet().getAbrigo().getNome() +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação.";
        emailService.enviarEmail(adocao.getPet().getAbrigo().getEmail(), "Solicitação de adoção", mensagem);
    }
    public void aprovar(AprovacaoAdocaoDTO dto){

        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.aprovar();

        var mensagem = "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet.";
        emailService.enviarEmail(adocao.getTutor().getEmail(), "Adoção aprovada", mensagem);
    }
    public void reprovar(ReprovacaoAdocaoDTO dto){
        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.reprovar(dto.justificativa());

        var mensagem = "Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +" com a seguinte justificativa: " +adocao.getJustificativaStatus();
        emailService.enviarEmail(adocao.getTutor().getEmail(), "Adoção reprovada", mensagem);
    }


}
